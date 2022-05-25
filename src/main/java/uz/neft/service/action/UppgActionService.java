package uz.neft.service.action;

import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.UppgDto;
import uz.neft.dto.action.ObjectWithActionsDto;
import uz.neft.dto.action.UppgActionDto;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.User;
import uz.neft.entity.action.UppgAction;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.UppgRepository;
import uz.neft.repository.UserRepository;
import uz.neft.repository.WellRepository;
import uz.neft.repository.action.CollectionPointActionRepository;
import uz.neft.repository.action.UppgActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UppgActionService {

    private final UserRepository userRepository;
    private final UppgActionRepository uppgActionRepository;
    private final UppgRepository uppgRepository;
    private final Converter converter;
    private final MiningSystemRepository miningSystemRepository;
    private final Logger logger;

    public UppgActionService(UserRepository userRepository, UppgActionRepository uppgActionRepository, UppgRepository uppgRepository, CollectionPointActionRepository collectionPointActionRepository, Converter converter, WellActionRepository wellActionRepository, WellRepository wellRepository, MiningSystemRepository miningSystemRepository, Logger logger) {
        this.userRepository = userRepository;
        this.uppgActionRepository = uppgActionRepository;
        this.uppgRepository = uppgRepository;
        this.converter = converter;
        this.miningSystemRepository = miningSystemRepository;
        this.logger = logger;
    }


    /**
     * Manually
     **/

    public HttpEntity<?> addManually(User user, UppgActionDto dto) {

        if (dto.getUppgId() == null) return converter.apiError400("Uppg id is null");
        Optional<Uppg> uppg = uppgRepository.findById(dto.getUppgId());
        if (!uppg.isPresent()) return converter.apiError400("uppg not found");

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

            UppgActionDto uppgActionDto = converter.uppgActionToUppgActionDto(save);

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

            List<UppgDto> collect = all.stream().map(converter::uppgToUppgDto).collect(Collectors.toList());

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
            if (!byId.isPresent()) return converter.apiError404("mining system not found");

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
            if (!byId1.isPresent()) return converter.apiError404("uppg not found");

            List<UppgAction> allByUppgOrderByCreatedAtDesc = uppgActionRepository.findAllByUppgOrderByCreatedAtDesc(byId1.get());
            Stream<UppgActionDto> uppgActionDtoStream = allByUppgOrderByCreatedAtDesc.stream().map(converter::uppgActionToUppgActionDto);

            return converter.apiSuccess200(uppgActionDtoStream);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching uppgs with actions by mining system ");
        }
    }

    public HttpEntity<?> getByMiningSystem(Integer id) {
        try {
            Optional<MiningSystem> byId = miningSystemRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("mining system not found");

            List<Uppg> uppgs = uppgRepository.findAllByMiningSystem(byId.get());

            List<UppgDto> collect = uppgs.stream().map(converter::uppgToUppgDto).collect(Collectors.toList());

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
            if (!byId.isPresent()) return converter.apiError404("uppg not found");
            UppgDto dto = converter.uppgToUppgDto(byId.get());
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
            if (!byId.isPresent()) return converter.apiError404("uppg not found");

            Optional<UppgAction> uppgAction = uppgActionRepository.findFirstByUppgOrderByCreatedAtDesc(byId.get());
//            if (!uppgAction.isPresent()) return converter.apiError404("uppg action not found");

            UppgActionDto uppgActionDto = new UppgActionDto();
            UppgDto uppgDto = converter.uppgToUppgDto(byId.get());
            if (uppgAction.isPresent()) {
                uppgActionDto = converter.uppgActionToUppgActionDto(uppgAction.get());
            } else {
                uppgActionDto = converter.uppgActionToUppgActionDto(null);
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
            if (!byId1.isPresent()) converter.apiSuccess200("Empty list");
            Optional<UppgAction> firstByUppg = uppgActionRepository.findFirstByUppgOrderByCreatedAtDesc(byId1.get());

            UppgDto uppgDto = converter.uppgToUppgDto(byId1.get());
            UppgActionDto uppgActionDto = new UppgActionDto();

            if (firstByUppg.isPresent()) {
                uppgActionDto = converter.uppgActionToUppgActionDto(firstByUppg.get());
            } else {
                uppgActionDto = converter.uppgActionToUppgActionDto(null);
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
                UppgActionDto uppgActionDto = converter.uppgActionToUppgActionDto(save);
                return converter.apiSuccess200("Uppg action edited", uppgActionDto);
            }
            return converter.apiError404("Uppg action not found");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error editing Uppg action");
        }
    }


    /** Auto **/

    //.....  from MODBUS
    //... coming soon
}
