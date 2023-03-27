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
import uz.neft.dto.UppgDto;
import uz.neft.dto.action.ObjectWithActionsDto;
import uz.neft.dto.action.UppgActionDto;
import uz.neft.dto.fake.FakeService;
import uz.neft.dto.fake.FakeUppg;
import uz.neft.entity.ForecastGas;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.User;
import uz.neft.entity.action.MiningSystemAction;
import uz.neft.entity.action.UppgAction;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UppgActionService {

    private final UserRepository userRepository;
    private final CollectionPointRepository collectionPointRepository;
    private final CollectionPointActionRepository collectionPointActionRepository;
    private final Converter converter;
    private final WellActionRepository wellActionRepository;
    private final WellRepository wellRepository;
    private final UppgRepository uppgRepository;
    private final UppgActionRepository uppgActionRepository;
    private final MiningSystemRepository miningSystemRepository;
    private final MiningSystemActionRepository miningSystemActionRepository;
    private final MiningSystemActionService miningSystemActionService;
    private final WellActionService wellActionService;
    private final OpcService opcService;
    private final FakeService fakeService;
    private final ForecastGasRepository forecastGasRepository;
    private final ForecastGasService forecastGasService;
    private final AkkaService akkaService;
    private final TESTForecastGasService testForecastGasService;
    private final Logger logger;

    @Value("${uppg.write.interval}")
    private long uppgWriteInterval;

    @Value("${uppg.simulation.enabled}")
    private boolean uppgSimulationEnabled;

    public UppgActionService(UserRepository userRepository, CollectionPointRepository collectionPointRepository, CollectionPointActionRepository collectionPointActionRepository1, UppgActionRepository uppgActionRepository, UppgRepository uppgRepository, CollectionPointActionRepository collectionPointActionRepository, Converter converter, WellActionRepository wellActionRepository, WellRepository wellRepository, WellActionRepository wellActionRepository1, WellRepository wellRepository1, MiningSystemRepository miningSystemRepository, MiningSystemActionRepository miningSystemActionRepository, MiningSystemActionService miningSystemActionService, WellActionService wellActionService, OpcService opcService, FakeService fakeService, ForecastGasRepository forecastGasRepository, ForecastGasService forecastGasService, AkkaService akkaService, TESTForecastGasService testForecastGasService, Logger logger) {
        this.userRepository = userRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.collectionPointActionRepository = collectionPointActionRepository1;
        this.uppgActionRepository = uppgActionRepository;
        this.uppgRepository = uppgRepository;
        this.converter = converter;
        this.wellActionRepository = wellActionRepository1;
        this.wellRepository = wellRepository1;
        this.miningSystemRepository = miningSystemRepository;
        this.miningSystemActionRepository = miningSystemActionRepository;
        this.miningSystemActionService = miningSystemActionService;
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

    public HttpEntity<?> addManually(User user, UppgActionDto dto) {

        if (dto.getUppgId() == null) return converter.apiError400("Uppg id is null");
        Optional<Uppg> uppg = uppgRepository.findById(dto.getUppgId());
        if (uppg.isEmpty()) return converter.apiError400("uppg not found");

        try {
            UppgAction uppgAction = UppgAction
                    .builder()
                    .user(userRepository.findById(1).get())
                    .designedPerformance(dto.getDesignedPerformance())
                    .actualPerformance(dto.getActualPerformance())
                    .expend(dto.getExpend())
                    .condensate(dto.getCondensate())
                    .onWater(dto.getOnWater())
                    .incomeTemperature(dto.getIncomeTemperature())
                    .exitTemperature(dto.getExitTemperature())
                    .incomePressure(dto.getIncomePressure())
                    .exitPressure(dto.getExitPressure())
                    .uppg(uppg.get())
                    .build();

            UppgAction save = uppgActionRepository.save(uppgAction);

            UppgActionDto uppgActionDto = (UppgActionDto) converter.dto(save);

            return converter.apiSuccess201(uppgActionDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409();
        }
    }

    public ResponseEntity<?> deleteUppgAction(Long id) {
        try {
            if (id != null) {
                Optional<UppgAction> byId = uppgActionRepository.findById(id);
                if (byId.isPresent()) {
                    uppgActionRepository.delete(byId.get());
                    return converter.apiSuccess200("Uppg action deleted");
                } else {
                    return converter.apiError404("UppgAction found");
                }
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in deleting uppg action", e);
        }
    }

    public HttpEntity<?> getUppgs() {
        try {
            List<Uppg> all = uppgRepository.findAll();

            List<UppgDto> collect = all.stream().map(uppg -> (UppgDto) converter.dto(uppg)).collect(Collectors.toList());

            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching all uppgs");
        }
    }

    public HttpEntity<?> getUppgsWithAction() {
        try {
            List<Uppg> all = uppgRepository.findAll();

            return uppgActionDtos(all);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching uppgs with actions");
        }
    }

    public HttpEntity<?> getUppgsWithActionByMiningSystem(Integer id) {
        try {
            Optional<MiningSystem> byId = miningSystemRepository.findById(id);
            if (byId.isEmpty()) return converter.apiError404("mining system not found");

            List<Uppg> uppgs = uppgRepository.findAllByMiningSystem(byId.get());

            return uppgActionDtos(uppgs);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching uppgs with actions by mining system ");
        }
    }

    public HttpEntity<?> getUppgActionsByUppgId(Integer id) {
        try {
            Optional<Uppg> byId1 = uppgRepository.findById(id);
            if (byId1.isEmpty()) return converter.apiError404("uppg not found");

            List<UppgAction> allByUppgOrderByCreatedAtDesc = uppgActionRepository.findAllByUppgOrderByCreatedAtDesc(byId1.get());
            Stream<UppgActionDto> uppgActionDtoStream = allByUppgOrderByCreatedAtDesc.stream().map(uppg -> (UppgActionDto) converter.dto(uppg));

            return converter.apiSuccess200(uppgActionDtoStream);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching uppgs with actions by mining system ");
        }
    }

    public HttpEntity<?> getUppgActionsByUppgId(Integer id,Optional<Integer> page,Optional<Integer> pageSize, Optional<String> sortBy) {
        try {
            Optional<Uppg> byId1 = uppgRepository.findById(id);
            if (byId1.isEmpty()) return converter.apiError404("uppg not found");
            Pageable pg = PageRequest.of(page.orElse(0), pageSize.orElse(10), Sort.Direction.DESC, sortBy.orElse("createdAt"));

            Page<UppgAction> uppgActions = uppgActionRepository.findAllByUppgOrderByCreatedAtDesc(byId1.get(), pg);

            Stream<UppgActionDto> uppgActionDtoStream = uppgActions.stream().map(uppg -> (UppgActionDto) converter.dto(uppg));

            return converter.apiSuccess200(uppgActionDtoStream,uppgActions.getTotalElements(),uppgActions.getTotalPages(),uppgActions.getNumber());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching uppgs with actions by mining system ");
        }
    }

    public HttpEntity<?> getByMiningSystem(Integer id) {
        try {
            Optional<MiningSystem> byId = miningSystemRepository.findById(id);
            if (byId.isEmpty()) return converter.apiError404("mining system not found");

            List<Uppg> uppgs = uppgRepository.findAllByMiningSystem(byId.get());

            List<UppgDto> collect = uppgs.stream().map(uppg -> (UppgDto) converter.dto(uppg)).collect(Collectors.toList());

            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching uppgs by mining System ");
        }
    }

    public HttpEntity<?> getUppg(Integer id) {
        try {
            Optional<Uppg> byId = uppgRepository.findById(id);
            if (byId.isEmpty()) return converter.apiError404("uppg not found");
            UppgDto dto = (UppgDto) converter.dto(byId.get());
            return converter.apiSuccess200(dto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching uppg by id");
        }
    }

    public HttpEntity<?> getUppgWithAction(Integer id) {
        try {
            Optional<Uppg> byId = uppgRepository.findById(id);
            if (byId.isEmpty()) return converter.apiError404("uppg not found");

            Optional<UppgAction> uppgAction = uppgActionRepository.findFirstByUppgOrderByCreatedAtDesc(byId.get());
//            if (!uppgAction.isPresent()) return converter.apiError404("uppg action not found");

            UppgActionDto uppgActionDto = new UppgActionDto();
            UppgDto uppgDto = (UppgDto) converter.dto(byId.get());
            if (uppgAction.isPresent()) {
                uppgActionDto = (UppgActionDto) converter.dto(uppgAction.get());
            } else {
                uppgActionDto = (UppgActionDto) converter.dto(null);
            }

            ObjectWithActionsDto dto = ObjectWithActionsDto
                    .builder()
                    .objectDto(uppgDto)
                    .objectActionDto(uppgActionDto)
                    .build();

            return converter.apiSuccess200(dto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching uppg by id eith action");
        }
    }

    // helper method
    private HttpEntity<?> uppgActionDtos(List<Uppg> uppgs) {
        if (uppgs.isEmpty()) return converter.apiSuccess200("Empty list");

        List<ObjectWithActionsDto> collect = uppgs.stream().map(item -> {
            Optional<Uppg> byId1 = uppgRepository.findById(item.getId());
            if (byId1.isEmpty()) converter.apiSuccess200("Empty list");
            Optional<UppgAction> firstByUppg = uppgActionRepository.findFirstByUppgOrderByCreatedAtDesc(byId1.get());

            UppgDto uppgDto = (UppgDto) converter.dto(byId1.get());
            UppgActionDto uppgActionDto = new UppgActionDto();

            if (firstByUppg.isPresent()) {
                uppgActionDto = (UppgActionDto) converter.dto(firstByUppg.get());
            } else {
                uppgActionDto = (UppgActionDto) converter.dto(null);
            }

            return ObjectWithActionsDto
                    .builder()
                    .objectDto(uppgDto)
                    .objectActionDto(uppgActionDto)
                    .build();
        }).collect(Collectors.toList());

        if (collect.isEmpty()) return converter.apiSuccess200("Empty List", null);
        else
            return converter.apiSuccess200(collect);
    }

    public HttpEntity<?> editAction(UppgActionDto dto) {
        try {
            if (dto.getActionId()== null) return converter.apiError400("Action Id is null");

            Optional<Uppg> uppg = uppgRepository.findById(dto.getUppgId());

            UppgAction uppgAction;
            Optional<UppgAction> byId = uppgActionRepository.findById(dto.getActionId());
            if (byId.isPresent()) {
                uppgAction = byId.get();
                uppgAction.setExpend(dto.getExpend());
                uppgAction.setUppg(uppg.get());
                uppgAction.setActualPerformance(dto.getActualPerformance());
                uppgAction.setCondensate(dto.getCondensate());
                uppgAction.setDesignedPerformance(dto.getDesignedPerformance());
                uppgAction.setExitPressure(dto.getExitPressure());
                uppgAction.setExitTemperature(dto.getExitTemperature());
                uppgAction.setIncomePressure(dto.getIncomePressure());
                uppgAction.setIncomeTemperature(dto.getIncomeTemperature());
                uppgAction.setOnWater(dto.getOnWater());
                UppgAction save = uppgActionRepository.save(uppgAction);
                UppgActionDto uppgActionDto = (UppgActionDto) converter.dto(save);
                return converter.apiSuccess200("Uppg action edited", uppgActionDto);
            }
            return converter.apiError404("Uppg action not found");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error editing Uppg action");
        }
    }


    public double random(double min, double max) {
        Random r = new Random();
        return r.nextDouble() * (max - min) + min;
    }

    /** Auto **/

    //.....  from MODBUS
    //... coming soon

    public void setAllUppgAction(List<Uppg> uppgs, MiningSystem miningSystem){

        if (uppgSimulationEnabled){


            List<UppgAction> uppgActions= new ArrayList<>();
            for (Uppg uppg : uppgs) {
                Optional<UppgAction> lastAction = uppgActionRepository.findFirstByUppgOrderByCreatedAtDesc(uppg);
                if (lastAction.isPresent()) {
                    Timestamp modifiedDate = new Timestamp(System.currentTimeMillis());
                    lastAction.get().setModified(modifiedDate);
                    lastAction.get().setExpend(random(300, 450));
                    lastAction.get().setIncomePressure(Calculator.mega_pascal_to_kgf_sm2(random(10, 12)));
                    lastAction.get().setExitPressure(Calculator.mega_pascal_to_kgf_sm2(random(10, 12)));
                    lastAction.get().setCondensate(lastAction.get().getCondensate());
                    lastAction.get().setIncomeTemperature(random(45, 52));
                    lastAction.get().setExitTemperature(random(45, 52));
                    lastAction.get().setOnWater(lastAction.get().getOnWater());
                    lastAction.get().setActualPerformance(random(15000000, 19000000));
                    lastAction.get().setDesignedPerformance(lastAction.get().getDesignedPerformance());
                    lastAction.get().setTodayExpend(random(1000000, 2000000));
                    lastAction.get().setYesterdayExpend(random(8000000, 11000000));
                    lastAction.get().setThisMonthExpend(random(50000000, 70000000));
                    lastAction.get().setLastMonthExpend(random(70000000, 100000000));
                    uppgActionRepository.save(lastAction.get());
                    uppgActions.add(lastAction.get());
                } else {
                    UppgAction action = UppgAction
                            .builder()
                            .uppg(uppg)
                            .expend(random(300, 450))
                            .incomePressure(random(10, 12))
                            .exitPressure(random(10, 12))
                            .condensate(0)
                            .exitTemperature(random(45, 52))
                            .incomeTemperature(random(45, 52))
                            .onWater(15)
                            .actualPerformance(random(15000000, 19000000))
                            .designedPerformance(0)
                            .todayExpend(random(1000000, 2000000))
                            .yesterdayExpend(random(8000000, 11000000))
                            .thisMonthExpend(random(50000000, 70000000))
                            .lastMonthExpend(random(70000000, 100000000))
                            .modified(new Timestamp(System.currentTimeMillis()))
                            .build();
                    action = uppgActionRepository.save(action);
                    uppgActions.add(action);
                }
            }

            miningSystemActionService.setAllAction(uppgActions, miningSystem);


        }else {
            List<FakeUppg> fakeUppgList = fakeService.all();
            List<UppgAction> uppgActions= new ArrayList<>();
            for (int i = 0; i < uppgs.size(); i++) {
                Optional<UppgAction> lastAction = uppgActionRepository.findFirstByUppgOrderByCreatedAtDesc(uppgs.get(i));
                if (lastAction.isPresent()){
                    Timestamp modifiedDate = new Timestamp(System.currentTimeMillis());
                    lastAction.get().setModified(modifiedDate);
                    lastAction.get().setExpend(fakeUppgList.get(i).getRasxod());
                    lastAction.get().setIncomePressure(Calculator.mega_pascal_to_kgf_sm2(fakeUppgList.get(i).getDavleniya()));
                    lastAction.get().setExitPressure(Calculator.mega_pascal_to_kgf_sm2(fakeUppgList.get(i).getDavleniya()));
                    lastAction.get().setCondensate(lastAction.get().getCondensate());
                    lastAction.get().setIncomeTemperature(fakeUppgList.get(i).getTemperatura());
                    lastAction.get().setExitTemperature(fakeUppgList.get(i).getTemperatura());
                    lastAction.get().setOnWater(lastAction.get().getOnWater());
                    lastAction.get().setActualPerformance(fakeUppgList.get(i).getNakoplenniy_obyom());
                    lastAction.get().setDesignedPerformance(lastAction.get().getDesignedPerformance());
                    lastAction.get().setTodayExpend(fakeUppgList.get(i).getNakoplenniy_obyom_s_nachalo_sutok());
                    lastAction.get().setYesterdayExpend(fakeUppgList.get(i).getNakoplenniy_obyom_za_vchera());
                    lastAction.get().setThisMonthExpend(fakeUppgList.get(i).getNakoplenniy_obyom_s_nachalo_mesyach());
                    lastAction.get().setLastMonthExpend(fakeUppgList.get(i).getNakoplenniy_obyom_za_pered_mesyach());
                    uppgActionRepository.save(lastAction.get());
                    uppgActions.add(lastAction.get());
                }else {
                    UppgAction action = UppgAction
                            .builder()
                            .uppg(uppgs.get(i))
                            .expend(fakeUppgList.get(i).getRasxod())
                            .incomePressure(Calculator.mega_pascal_to_kgf_sm2(fakeUppgList.get(i).getDavleniya()))
                            .exitPressure(Calculator.mega_pascal_to_kgf_sm2(fakeUppgList.get(i).getDavleniya()))
                            .condensate(0)
                            .exitTemperature(fakeUppgList.get(i).getTemperatura())
                            .incomeTemperature(fakeUppgList.get(i).getTemperatura())
                            .onWater(15)
                            .actualPerformance(fakeUppgList.get(i).getNakoplenniy_obyom())
                            .designedPerformance(0)
                            .todayExpend(fakeUppgList.get(i).getNakoplenniy_obyom_s_nachalo_sutok())
                            .yesterdayExpend(fakeUppgList.get(i).getNakoplenniy_obyom_za_vchera())
                            .thisMonthExpend(fakeUppgList.get(i).getNakoplenniy_obyom_s_nachalo_mesyach())
                            .lastMonthExpend(fakeUppgList.get(i).getNakoplenniy_obyom_za_pered_mesyach())
                            .modified(new Timestamp(System.currentTimeMillis()))
                            .build();
                    action = uppgActionRepository.save(action);
                    uppgActions.add(action);
                }
            }

            miningSystemActionService.setAllAction(uppgActions, miningSystem);
        }






//        if (fakeUppgList.size() == 2 && uppgs.size() <= 2) {
//            UppgAction uppgAction1 = UppgAction
//                    .builder()
//                    .uppg(uppgs.get(0))
//                    .expend(fakeUppgList.get(0).getRasxod())
//                    .incomePressure(Calculator.mega_pascal_to_kgf_sm2(fakeUppgList.get(0).getDavleniya()))
//                    .exitPressure(Calculator.mega_pascal_to_kgf_sm2(fakeUppgList.get(0).getDavleniya()))
//                    .condensate(0)
//                    .exitTemperature(fakeUppgList.get(0).getTemperatura())
//                    .incomeTemperature(fakeUppgList.get(0).getTemperatura())
//                    .onWater(15)
//                    .actualPerformance(0)
//                    .designedPerformance(0)
//                    .todayExpend(fakeUppgList.get(0).getNakoplenniy_obyom_s_nachalo_sutok())
//                    .yesterdayExpend(fakeUppgList.get(0).getNakoplenniy_obyom_za_vchera())
//                    .thisMonthExpend(fakeUppgList.get(0).getNakoplenniy_obyom_s_nachalo_mesyach())
//                    .lastMonthExpend(fakeUppgList.get(0).getNakoplenniy_obyom_za_pered_mesyach())
//                    .build();
//            uppgAction1 = uppgActionRepository.save(uppgAction1);
//
//            UppgAction uppgAction2 = UppgAction
//                    .builder()
//                    .uppg(uppgs.get(1))
//                    .expend(fakeUppgList.get(1).getRasxod())
//                    .incomePressure(Calculator.mega_pascal_to_kgf_sm2(fakeUppgList.get(1).getDavleniya()))
//                    .exitPressure(Calculator.mega_pascal_to_kgf_sm2(fakeUppgList.get(1).getDavleniya()))
//                    .condensate(0)
//                    .exitTemperature(fakeUppgList.get(1).getTemperatura())
//                    .incomeTemperature(fakeUppgList.get(1).getTemperatura())
//                    .onWater(15)
//                    .actualPerformance(0)
//                    .designedPerformance(0)
//                    .todayExpend(fakeUppgList.get(1).getNakoplenniy_obyom_s_nachalo_sutok())
//                    .yesterdayExpend(fakeUppgList.get(1).getNakoplenniy_obyom_za_vchera())
//                    .thisMonthExpend(fakeUppgList.get(1).getNakoplenniy_obyom_s_nachalo_mesyach())
//                    .lastMonthExpend(fakeUppgList.get(1).getNakoplenniy_obyom_za_pered_mesyach())
//                    .build();
//            uppgAction2 = uppgActionRepository.save(uppgAction2);
//
//            miningSystemActionService.setAllAction(List.of(uppgAction1, uppgAction2), miningSystem);
//
//        }
    }
}
