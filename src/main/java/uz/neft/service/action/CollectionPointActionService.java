package uz.neft.service.action;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.controller.opc.OpcService;
import uz.neft.dto.CollectionPointDto;
import uz.neft.dto.action.CollectionPointActionDto;
import uz.neft.dto.action.ObjectWithActionsDto;
import uz.neft.dto.fake.FakeService;
import uz.neft.dto.fake.FakeUppg;
import uz.neft.dto.special.CollectionPointAndWells;
import uz.neft.entity.*;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.entity.action.MiningSystemAction;
import uz.neft.entity.action.UppgAction;
import uz.neft.entity.action.WellAction;
import uz.neft.entity.enums.OpcServerType;
import uz.neft.entity.enums.WellStatus;
import uz.neft.repository.*;
import uz.neft.repository.action.CollectionPointActionRepository;
import uz.neft.repository.action.MiningSystemActionRepository;
import uz.neft.repository.action.UppgActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.repository.constants.ForecastGasRepository;
import uz.neft.service.AkkaService;
import uz.neft.service.Calculator;
import uz.neft.service.ForecastGasService;
import uz.neft.service.TESTForecastGasService;
import uz.neft.utils.Converter;

import java.sql.Timestamp;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CollectionPointActionService {

    private final UserRepository userRepository;
    private final CollectionPointRepository collectionPointRepository;
    private final CollectionPointActionRepository collectionPointActionRepository;
    private final Converter converter;
    private final WellActionRepository wellActionRepository;
    private final WellRepository wellRepository;
    private final UppgRepository uppgRepository;
    private final UppgActionRepository uppgActionRepository;
    private final UppgActionService uppgActionService;
    private final MiningSystemRepository miningSystemRepository;
    private final MiningSystemActionRepository miningSystemActionRepository;
    private final WellActionService wellActionService;
    private final OpcService opcService;
    private final FakeService fakeService;
    private final ForecastGasRepository forecastGasRepository;
    private final ForecastGasService forecastGasService;
    private final AkkaService akkaService;
    private final TESTForecastGasService testForecastGasService;
    private final Logger logger;

    @Value("${delta.temperature.forCollectionPoint.Action}")
    private Double deltaTemperatureAction ;

    @Value("${delta.pressure.forCollectionPoint.Action}")
    private Double deltaPressureAction ;

    @Value("${check.time}")
    private long checkTime ;

    @Value("${write.interval}")
    private long writeTimeInterval ;

    public CollectionPointActionService(UserRepository userRepository, CollectionPointRepository collectionPointRepository, CollectionPointActionRepository collectionPointActionRepository, Converter converter, WellActionRepository wellActionRepository, WellRepository wellRepository, UppgRepository uppgRepository, UppgActionRepository uppgActionRepository, UppgActionService uppgActionService, MiningSystemRepository miningSystemRepository, MiningSystemActionRepository miningSystemActionRepository, WellActionService wellActionService, OpcService opcService, FakeService fakeService, ForecastGasRepository forecastGasRepository, ForecastGasService forecastGasService, AkkaService akkaService, TESTForecastGasService testForecastGasService, Logger logger) {
        this.userRepository = userRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.collectionPointActionRepository = collectionPointActionRepository;
        this.converter = converter;
        this.wellActionRepository = wellActionRepository;
        this.wellRepository = wellRepository;
        this.uppgRepository = uppgRepository;
        this.uppgActionRepository = uppgActionRepository;
        this.uppgActionService = uppgActionService;
        this.miningSystemRepository = miningSystemRepository;
        this.miningSystemActionRepository = miningSystemActionRepository;
        this.wellActionService = wellActionService;
        this.opcService = opcService;
        this.fakeService = fakeService;
        this.forecastGasRepository = forecastGasRepository;
        this.forecastGasService = forecastGasService;
        this.akkaService = akkaService;
        this.testForecastGasService = testForecastGasService;
        this.logger = logger;
    }

    /**
     * Manually
     **/
    public HttpEntity<?> addManually(User user, CollectionPointActionDto dto) {

        if (dto.getCollectionPointId() == null) return converter.apiError400("Collection Point id is null");
        Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(dto.getCollectionPointId());
        if (!collectionPoint.isPresent()) return converter.apiError400("Collection Point not found");

        List<Well> allByCollectionPoint = wellRepository.findAllByCollectionPointOrderByIdAsc(collectionPoint.get());

        double D_sp = 0;

        for (Well well : allByCollectionPoint) {
            Optional<WellAction> wellAction = wellActionRepository.findFirstByWell(well);
            if (wellAction.isPresent()) {
                D_sp += wellAction.get().getExpend();
            }
        }

        try {

            CollectionPointAction collectionPointAction = CollectionPointAction
                    .builder()
                    .user(userRepository.findById(1).get())
                    .pressure(dto.getPressure())
                    .temperature(dto.getTemperature())
                    .expend(D_sp)
                    .collectionPoint(collectionPoint.get())
                    .build();

            if (collectionPointAction.getTemperature() == 0 || collectionPointAction.getPressure() == 0)
                collectionPointAction.setExpend(0);

            CollectionPointAction save = collectionPointActionRepository.save(collectionPointAction);

            CollectionPointActionDto collectionPointActionDto = converter.collectionPointActionToCollectionPointActionDto(save);

            return converter.apiSuccess201(collectionPointActionDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409();
        }
    }

    public HttpEntity<?> addSpecial(User user, CollectionPointAndWells collectionPointAndWells) {
        try {
            Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(collectionPointAndWells.getCollectionPointId());
            if (!collectionPoint.isPresent()) return converter.apiError404("Collection point not found");
            ResponseEntity<?> response = wellActionService.addSpecial(user, collectionPoint.get(), collectionPointAndWells.getWellList());
            if (response.getStatusCode().value() != 200) return response;
            CollectionPointActionDto dto = CollectionPointActionDto
                    .builder()
                    .collectionPointId(collectionPoint.get().getId())
                    .pressure(collectionPointAndWells.getPressure() > 0 ? collectionPointAndWells.getPressure() : 0)
                    .temperature(collectionPointAndWells.getTemperature() > 0 ? collectionPointAndWells.getTemperature() : 0)
                    .build();
            return addManually(user, dto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409();
        }
    }

    public HttpEntity<?> getCollectionPoints() {
        try {
            List<CollectionPoint> all = collectionPointRepository.findAll();

            List<CollectionPointDto> collect = all.stream().map(converter::collectionPointToCollectionPointDto).collect(Collectors.toList());

            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching all collection point names");
        }
    }

    public HttpEntity<?> getAllByMiningSystem(Integer id) {
        try {
            if (id == null) return converter.apiError400("id is null!");
//            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
//            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found!");
//            List<Uppg> uppgList = uppgRepository.findAllByMiningSystem(miningSystem.get());
//            List<CollectionPoint> collectionPointList =new ArrayList<>();
            List<CollectionPoint> collectionPointList = collectionPointRepository.findAllByMiningSystemId(id);
//            for (Uppg uppg : uppgList) {
//                List<CollectionPoint> allByUppg = collectionPointRepository.findAllByUppg(uppg);
//                collectionPointList.addAll(allByUppg);
//            }
            return converter.apiSuccess200(collectionPointList.stream().map(converter::collectionPointToCollectionPointDto).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409();
        }
    }

    public HttpEntity<?> getAllWithActionsByMiningSystem(Integer id) {
        try {
            if (id == null) return converter.apiError400("id is null!");
            List<CollectionPoint> collectionPointList = collectionPointRepository.findAllByMiningSystemIdOrder(id);
            List<ObjectWithActionsDto> list = new ArrayList<>();

//            collectionPointList.forEach(c->System.out.println(collectionPointActionRepository.findFirstByCollectionPointOrderByCreatedAtDesc(c)));

            collectionPointList
                    .forEach(
                            c ->
                                    list
                                            .add(new ObjectWithActionsDto(
                                                    converter.collectionPointToCollectionPointDto(c),
                                                    converter.collectionPointActionToCollectionPointActionDto(
                                                            collectionPointActionRepository
                                                                    .findFirstByCollectionPointOrderByCreatedAtDesc(c).orElse(null)))));
            return converter.apiSuccess200(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409();
        }
    }

    public HttpEntity<?> getAllActionsByCollectionPoint(Integer collectionPointId, Optional<Integer> page, Optional<Integer> pageSize, Optional<String> sortBy) {
        try {
            if (collectionPointId == null) return converter.apiError400("action id is null!");
            Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(collectionPointId);
            if (!collectionPoint.isPresent()) return converter.apiError404("collection point not found");

//            List<CollectionPointAction> collectionPointActions = collectionPointActionRepository.findAllByCollectionPointOrderByCreatedAtDesc(collectionPoint.get());
            Pageable pg = PageRequest.of(page.orElse(0), pageSize.orElse(10), Sort.Direction.DESC, sortBy.orElse("createdAt"));

            Page<CollectionPointAction> collectionPointActions = collectionPointActionRepository.findAllByCollectionPointOrderByCreatedAtDesc(collectionPoint.get(), pg);

            Stream<CollectionPointActionDto> collectionPointActionDtoStream = collectionPointActions.stream().map(converter::collectionPointActionToCollectionPointActionDto);

            return converter.apiSuccess200(collectionPointActionDtoStream, collectionPointActions.getTotalElements(), collectionPointActions.getTotalPages(), collectionPointActions.getNumber());
//            return converter.apiSuccess200(collectionPointActionDtoStream);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409();
        }
    }

    public HttpEntity<?> getByUppg(Integer id) {
        try {
            Optional<Uppg> byId = uppgRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("uppg not found");

            List<CollectionPoint> allByUppg = collectionPointRepository.findAllByUppgOrderByIdAsc(byId.get());

            List<CollectionPointDto> collect = allByUppg.stream().map(converter::collectionPointToCollectionPointDto).collect(Collectors.toList());

            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching collection point by uppg ");
        }
    }

    public HttpEntity<?> getCollectionPoint(Integer id) {
        try {
            Optional<CollectionPoint> byId = collectionPointRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("collection point not found");

            CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(byId.get());

            return converter.apiSuccess200(collectionPointDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching collection point by id");
        }
    }

    public HttpEntity<?> getCollectionPointsWithActions() {
        try {
            List<CollectionPoint> all = collectionPointRepository.findAll();

            List<ObjectWithActionsDto> collect = all.stream().map(item -> {
                Optional<CollectionPoint> byId = collectionPointRepository.findById(item.getId());
                Optional<CollectionPointAction> collectionPointAction = collectionPointActionRepository.findFirstByCollectionPointOrderByCreatedAtDesc(byId.get());

                CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(byId.get());
                CollectionPointActionDto collectionPointActionDto = converter.collectionPointActionToCollectionPointActionDto(collectionPointAction.get());

                return ObjectWithActionsDto
                        .builder()
                        .objectDto(collectionPointDto)
                        .objectActionDto(collectionPointActionDto)
                        .build();
            }).collect(Collectors.toList());

            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching collection points with actions");
        }
    }

    public HttpEntity<?> getCollectionPointWithAction(Integer id) {
        try {
            Optional<CollectionPoint> byId = collectionPointRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("collection point not found");

            Optional<CollectionPointAction> collectionPointAction = collectionPointActionRepository.findFirstByCollectionPointOrderByCreatedAtDesc(byId.get());

            CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(byId.get());
            CollectionPointActionDto collectionPointActionDto = converter.collectionPointActionToCollectionPointActionDto(collectionPointAction.get());

            ObjectWithActionsDto dto = ObjectWithActionsDto
                    .builder()
                    .objectDto(collectionPointDto)
                    .objectActionDto(collectionPointActionDto)
                    .build();

            return converter.apiSuccess200(dto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching collection point by id");
        }
    }

    public HttpEntity<?> deleteAction(Long id) {
        try {
            if (id != null) {
                Optional<CollectionPointAction> action = collectionPointActionRepository.findById(id);

                if (action.isPresent()) {
                    collectionPointActionRepository.delete(action.get());
                    return converter.apiSuccess200("Collection point action deleted");
                } else {
                    return converter.apiError404("Collection point Action found");
                }
            }
            return converter.apiError400("Action Id null");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in deleting Collection point action", e);
        }

    }

    public HttpEntity<?> editAction(CollectionPointActionDto dto) {
        try {
            Timestamp modifiedDate = new Timestamp(System.currentTimeMillis());
            if (dto.getActionId() == null) return converter.apiError400("Action Id is null");
            CollectionPointAction collectionPointAction;
            Optional<CollectionPointAction> action = collectionPointActionRepository.findById(dto.getActionId());
            if (action.isPresent()) {
                Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(dto.getCollectionPointId());
                if (collectionPoint.isPresent()) {
                    collectionPointAction = action.get();
                    collectionPointAction.setCollectionPoint(collectionPoint.get());
                    collectionPointAction.setExpend(dto.getExpend());
                    collectionPointAction.setPressure(dto.getPressure());
                    collectionPointAction.setTemperature(dto.getTemperature());
                    collectionPointAction.setModified(modifiedDate);

                    CollectionPointAction save = collectionPointActionRepository.save(collectionPointAction);
                    CollectionPointActionDto collectionPointActionDto = converter.collectionPointActionToCollectionPointActionDto(save);
                    return converter.apiSuccess200("Collection Point Action Edited", collectionPointActionDto);
                }
                return converter.apiError404("Collection Point not found");
            }
            return converter.apiError404("Collection Point Action not found!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error edit collection point");
        }
    }

    /**
     * Auto
     **/

    //..... from MODBUS

    //... coming soon
    public void setAll(int id) {
        try {
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(1);
            if (miningSystem.isPresent()) {
                List<Uppg> uppgs = uppgRepository.findAllByMiningSystem(miningSystem.get());

                try {
                    // Uppg processing
                    uppgActionService.setAllUppgAction(uppgs, miningSystem.get());
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.toString());
                }

                List<CollectionPoint> all = collectionPointRepository.findAllByMiningSystemId(id);

                List<CollectionPointAction> actionList=new ArrayList<>();

                setAllCollectionPoint(id);
//                actionList.forEach(akkaService::calculate);
            }
            testForecastGasService.addNewForecast(1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
    }

    public double checkWells(CollectionPoint cp, CollectionPointAction cpAction) {
        double expendCp = 0;
        try {
            List<Well> wellList = wellRepository.findAllByCollectionPointOrderByIdAsc(cp);
            for (Well well : wellList) {
                Optional<WellAction> action = wellActionRepository.findFirstByWellOrderByCreatedAtDesc(well);
                if (action.isPresent()) {
                    if (action.get().getPressure() < cpAction.getPressure()) {
                        action.get().setExpend(0);
                        if (action.get().getStatus() == WellStatus.IN_WORK)
                            action.get().setStatus(WellStatus.IN_IDLE);
                    } else {
                        if (action.get().getStatus() == WellStatus.IN_IDLE)
                            action.get().setStatus(WellStatus.IN_WORK);
                        double expend = wellActionService.expend(action.get().getTemperature(), action.get().getPressure(), cp.getUppg().getMiningSystem());
                        action.get().setAverage_expend(expend);
                        expendCp += action.get().getExpend();
                    }
                    wellActionRepository.save(action.get());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return expendCp;
    }


    public void setAllCollectionPoint(int id) throws InterruptedException {
        List<CollectionPoint> collectionPointList = collectionPointRepository.findAllByMiningSystemId(id);
        List<Uppg> uppgList=uppgRepository.findAll();
        for (CollectionPoint collectionPoint : collectionPointList) {

            if(collectionPoint.isActiveE()){

                Optional<CollectionPointAction> last = collectionPointActionRepository.findFirstByCollectionPointOrderByCreatedAtDesc(collectionPoint);

                CollectionPointAction action;
                if (last.isPresent()){
                    action=balancer(last.get());
                }else {
                    action=balancer(CollectionPointAction.builder().collectionPoint(collectionPoint).build());
                }



                Thread.sleep(100);
                if (action.getPressure() == 0
//                        || action.getTemperature() == 0
                ) {
                    action.setExpend(0);
//                    collectionPointActionRepository.save(action);
                } else {
                    double expendCp = checkWells(collectionPoint, action);
                    action.setExpend(expendCp);
                    logger.info("Expend "+collectionPoint.getName()+" = "+expendCp);
                    System.out.println("Expend "+collectionPoint.getName()+" = "+expendCp);
                }

                collectionPointActionRepository.save(action);
            }

//            wellActionService.execute(collectionPoint.getUppg());
        }
        for (Uppg uppg : uppgList) {
            wellActionService.execute(uppg);
        }

    }

    public CollectionPointAction balancer(CollectionPointAction last){

        String str=opcService.getValueWeb(last);
        double t = opcService.getValueWeb(str,last, last.getCollectionPoint().getTemperatureUnit());
        double p=0;
        if (last.getCollectionPoint().getOpcServer().getType().equals(OpcServerType.SIMULATE))
            p = opcService.getValueWeb(str,last, last.getCollectionPoint().getPressureUnit());
        else
            p = Calculator.mega_pascal_to_kgf_sm2(opcService.getValueWeb(str,last, last.getCollectionPoint().getPressureUnit()));

        if (Math.abs(t-last.getTemperature())>deltaTemperatureAction||Math.abs(p-last.getPressure())>deltaPressureAction){
            if (last.getCreatedAt()!=null){
                if (last.getModified()!=null &&
                        t==0
                        &&
                        p==0
                        &&
                        ((System.currentTimeMillis() - last.getModified().getTime()) < checkTime)){
                    return last;
                }
                else {
                    if (t==0
                            &&
                            p==0
                            &&
                            (System.currentTimeMillis()-last.getCreatedAt().getTime())<checkTime)
                        return last;
                }
            }

            return CollectionPointAction.builder().collectionPoint(last.getCollectionPoint()).pressure(p).temperature(t).build();
        }else {
            if (System.currentTimeMillis()-last.getCreatedAt().getTime()>=writeTimeInterval&&(last.getPressure()>0||last.getTemperature()>0)){
                return CollectionPointAction.builder().collectionPoint(last.getCollectionPoint()).pressure(p).temperature(t).build();
            }
            Timestamp modifiedDate = new Timestamp(System.currentTimeMillis());
            last.setModified(modifiedDate);

            return last;
        }

    }
}
