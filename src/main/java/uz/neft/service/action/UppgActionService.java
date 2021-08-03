package uz.neft.service.action;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.UppgDto;
import uz.neft.dto.WellDto;
import uz.neft.dto.action.ObjectWithActionsDto;
import uz.neft.dto.action.UppgActionDto;
import uz.neft.dto.action.WellActionDto;
import uz.neft.entity.*;
import uz.neft.entity.action.UppgAction;
import uz.neft.entity.action.WellAction;
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

@Service
public class UppgActionService {

    private final UserRepository userRepository;
    private final UppgActionRepository uppgActionRepository;
    private final UppgRepository uppgRepository;
    private final CollectionPointActionRepository collectionPointActionRepository;
    private final Converter converter;
    private final WellActionRepository wellActionRepository;
    private final WellRepository wellRepository;
    private final MiningSystemRepository miningSystemRepository;


    public UppgActionService(UserRepository userRepository, UppgActionRepository uppgActionRepository, UppgRepository uppgRepository, CollectionPointActionRepository collectionPointActionRepository, Converter converter, WellActionRepository wellActionRepository, WellRepository wellRepository, MiningSystemRepository miningSystemRepository) {
        this.userRepository = userRepository;
        this.uppgActionRepository = uppgActionRepository;
        this.uppgRepository = uppgRepository;
        this.collectionPointActionRepository = collectionPointActionRepository;
        this.converter = converter;
        this.wellActionRepository = wellActionRepository;
        this.wellRepository = wellRepository;
        this.miningSystemRepository = miningSystemRepository;
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
            return converter.apiError409();
        }
    }

    public HttpEntity<?> getUppgs() {
        try {
            List<Uppg> all = uppgRepository.findAll();

            List<UppgDto> collect = all.stream().map(converter::uppgToUppgDto).collect(Collectors.toList());

            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching all uppgs");
        }
    }

    public HttpEntity<?> getUppgsWithAction() {
        try {
            List<Uppg> all = uppgRepository.findAll();

            return uppgActionDtos(all);
        } catch (Exception e) {
            e.printStackTrace();
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
            return converter.apiError409("Error in fetching uppg by id");
        }
    }

    public HttpEntity<?> getUppgWithAction(Integer id) {
        try {
            Optional<Uppg> byId = uppgRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("uppg not found");

            UppgAction uppgAction = uppgActionRepository.findFirstByUppg(byId.get());
//            if (!uppgAction.isPresent()) return converter.apiError404("uppg action not found");

            UppgActionDto dto = converter.uppgActionToUppgActionDto(uppgAction);

            return converter.apiSuccess200(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching uppg by id eith action");
        }
    }

    // helper method
    private HttpEntity<?> uppgActionDtos(List<Uppg> uppgs) {
        List<ObjectWithActionsDto> collect = uppgs.stream().map(item -> {

            Optional<Uppg> byId1 = uppgRepository.findById(item.getId());
            UppgAction firstByUppg = uppgActionRepository.findFirstByUppg(byId1.get());

            UppgDto uppgDto = converter.uppgToUppgDto(byId1.get());
            UppgActionDto uppgActionDto = converter.uppgActionToUppgActionDto(firstByUppg);

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


    /** Auto **/

    //.....  from MODBUS
    //... coming soon
}
