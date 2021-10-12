package uz.neft.service.action;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.CollectionPointDto;
import uz.neft.dto.action.CollectionPointActionDto;
import uz.neft.dto.action.ObjectWithActionsDto;
import uz.neft.dto.special.CollectionPointAndWells;
import uz.neft.entity.*;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.entity.action.WellAction;
import uz.neft.entity.enums.WellStatus;
import uz.neft.repository.*;
import uz.neft.repository.action.CollectionPointActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollectionPointActionService {

    private final UserRepository userRepository;
    private final CollectionPointRepository collectionPointRepository;
    private final CollectionPointActionRepository collectionPointActionRepository;
    private final Converter converter;
    private final WellActionRepository wellActionRepository;
    private final WellRepository wellRepository;
    private final UppgRepository uppgRepository;
    private final MiningSystemRepository miningSystemRepository;
    private final WellActionService wellActionService;


    public CollectionPointActionService(UserRepository userRepository, CollectionPointRepository collectionPointRepository, CollectionPointActionRepository collectionPointActionRepository, Converter converter, WellActionRepository wellActionRepository, WellRepository wellRepository, UppgRepository uppgRepository, MiningSystemRepository miningSystemRepository, WellActionService wellActionService) {
        this.userRepository = userRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.collectionPointActionRepository = collectionPointActionRepository;
        this.converter = converter;
        this.wellActionRepository = wellActionRepository;
        this.wellRepository = wellRepository;
        this.uppgRepository = uppgRepository;
        this.miningSystemRepository = miningSystemRepository;
        this.wellActionService = wellActionService;
    }


    /**
     * Manually
     **/
    public HttpEntity<?> addManually(User user, CollectionPointActionDto dto) {


        if (dto.getCollectionPointId() == null) return converter.apiError400("Collection Point id is null");
        Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(dto.getCollectionPointId());
        if (!collectionPoint.isPresent()) return converter.apiError400("Collection Point not found");

        List<Well> allByCollectionPoint = wellRepository.findAllByCollectionPoint(collectionPoint.get());

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

            CollectionPointAction save = collectionPointActionRepository.save(collectionPointAction);

            CollectionPointActionDto collectionPointActionDto = converter.collectionPointActionToCollectionPointActionDto(save);

            return converter.apiSuccess201(collectionPointActionDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }


    public HttpEntity<?> addSpecial(User user, CollectionPointAndWells collectionPointAndWells){
        try {
            Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(collectionPointAndWells.getCollectionPointId());
            if (!collectionPoint.isPresent()) return converter.apiError404("Collection point not found");
            ResponseEntity<?> response = wellActionService.addSpecial(user, collectionPoint.get(), collectionPointAndWells.getWellList());
            if (response.getStatusCode().value()!=200) return response;
            CollectionPointActionDto dto= CollectionPointActionDto
                    .builder()
                    .collectionPointId(collectionPoint.get().getId())
                    .pressure(collectionPointAndWells.getPressure()>0?collectionPointAndWells.getPressure():0)
                    .temperature(collectionPointAndWells.getTemperature()>0?collectionPointAndWells.getTemperature():0)
                    .build();
            return addManually(user, dto);
        }catch (Exception e){
            e.printStackTrace();
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
            return converter.apiError409("Error in fetching all collection point names");
        }
    }

    public HttpEntity<?> getAllByMiningSystem(Integer id){
        try{
            if (id==null) return converter.apiError400("id is null!");
//            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
//            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found!");
//            List<Uppg> uppgList = uppgRepository.findAllByMiningSystem(miningSystem.get());
//            List<CollectionPoint> collectionPointList =new ArrayList<>();
            List<CollectionPoint> collectionPointList =collectionPointRepository.findAllByMiningSystemId(id);
//            for (Uppg uppg : uppgList) {
//                List<CollectionPoint> allByUppg = collectionPointRepository.findAllByUppg(uppg);
//                collectionPointList.addAll(allByUppg);
//            }
            return converter.apiSuccess200(collectionPointList.stream().map(converter::collectionPointToCollectionPointDto).collect(Collectors.toList()));
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public HttpEntity<?> getAllWithActionsByMiningSystem(Integer id){
        try{
            if (id==null) return converter.apiError400("id is null!");
            List<CollectionPoint> collectionPointList =collectionPointRepository.findAllByMiningSystemId(id);
            List<ObjectWithActionsDto> list=new ArrayList<>();

//            collectionPointList.forEach(c->System.out.println(collectionPointActionRepository.findFirstByCollectionPointOrderByCreatedAtDesc(c)));

            collectionPointList
                    .forEach(
                            c->
                            list
                                    .add(new ObjectWithActionsDto(
                                            converter.collectionPointToCollectionPointDto(c),
                                            converter.collectionPointActionToCollectionPointActionDto(
                                                    collectionPointActionRepository
                                                    .findFirstByCollectionPointOrderByCreatedAtDesc(c)))));
            return converter.apiSuccess200(list);
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public HttpEntity<?> getByUppg(Integer id) {
        try {
            Optional<Uppg> byId = uppgRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("uppg not found");

            List<CollectionPoint> allByUppg = collectionPointRepository.findAllByUppg(byId.get());

            List<CollectionPointDto> collect = allByUppg.stream().map(converter::collectionPointToCollectionPointDto).collect(Collectors.toList());

            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
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
            return converter.apiError409("Error in fetching collection point by id");
        }
    }

    public HttpEntity<?> getCollectionPointsWithActions() {
        try {
            List<CollectionPoint> all = collectionPointRepository.findAll();

            List<ObjectWithActionsDto> collect = all.stream().map(item -> {
                Optional<CollectionPoint> byId = collectionPointRepository.findById(item.getId());
                CollectionPointAction collectionPointAction = collectionPointActionRepository.findFirstByCollectionPointOrderByCreatedAtDesc(byId.get());

                CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(byId.get());
                CollectionPointActionDto collectionPointActionDto = converter.collectionPointActionToCollectionPointActionDto(collectionPointAction);

                return ObjectWithActionsDto
                        .builder()
                        .objectDto(collectionPointDto)
                        .objectActionDto(collectionPointActionDto)
                        .build();
            }).collect(Collectors.toList());

            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching collection points with actions");
        }
    }

    public HttpEntity<?> getCollectionPointWithAction(Integer id) {
        try {
            Optional<CollectionPoint> byId = collectionPointRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("collection point not found");

            CollectionPointAction collectionPointAction = collectionPointActionRepository.findFirstByCollectionPointOrderByCreatedAtDesc(byId.get());

            CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(byId.get());
            CollectionPointActionDto collectionPointActionDto = converter.collectionPointActionToCollectionPointActionDto(collectionPointAction);

            ObjectWithActionsDto dto = ObjectWithActionsDto
                    .builder()
                    .objectDto(collectionPointDto)
                    .objectActionDto(collectionPointActionDto)
                    .build();

            return converter.apiSuccess200(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching collection point by id");
        }
    }


    /** Auto **/

    //..... from MODBUS
    //... coming soon

    public void setAll(int id){
        try {

            List<CollectionPoint> all = collectionPointRepository.findAllByMiningSystemId(id);
            for (CollectionPoint collectionPoint : all) {
                CollectionPointAction action = CollectionPointAction
                        .builder()
                        .collectionPoint(collectionPoint)
                        .build();
                action.setPressure(action.getPressureOpc());
//                Thread.sleep(1000);
                action.setTemperature(action.getTemperatureOpc());

                Thread.sleep(500);
//                System.out.println(action.toString());
                double expendCp = checkWells(collectionPoint, action);
                action.setExpend(expendCp);
                collectionPointActionRepository.save(action);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public double checkWells(CollectionPoint cp,CollectionPointAction cpAction){
        double expendCp=0;
        try{
            List<Well> wellList = wellRepository.findAllByCollectionPoint(cp);
            for (Well well : wellList) {
                Optional<WellAction> action = wellActionRepository.findFirstByWellOrderByCreatedAtDesc(well);
                if (action.isPresent()) {
                    if (action.get().getPressure() < cpAction.getPressure()) {
                        action.get().setExpend(0);
                        if (action.get().getStatus()==WellStatus.IN_WORK)
                        action.get().setStatus(WellStatus.IN_IDLE);
                    }
                    else{
                        if (action.get().getStatus()==WellStatus.IN_IDLE)
                        action.get().setStatus(WellStatus.IN_WORK);
                        double expend = wellActionService.expend(action.get().getTemperature(), action.get().getPressure(), cp.getUppg().getMiningSystem());
                        action.get().setExpend(expend);
                        expendCp+=expend;
                    }
                    wellActionRepository.save(action.get());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return expendCp;
    }


}
