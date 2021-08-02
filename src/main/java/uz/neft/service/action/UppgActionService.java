package uz.neft.service.action;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.action.CollectionPointActionDto;
import uz.neft.dto.action.UppgActionDto;
import uz.neft.entity.Uppg;
import uz.neft.entity.User;
import uz.neft.entity.action.UppgAction;
import uz.neft.repository.UppgRepository;
import uz.neft.repository.UserRepository;
import uz.neft.repository.WellRepository;
import uz.neft.repository.action.CollectionPointActionRepository;
import uz.neft.repository.action.UppgActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.utils.Converter;

import java.util.Optional;

@Service
public class UppgActionService {

    private final UserRepository userRepository;
    private final UppgActionRepository uppgActionRepository;
    private final UppgRepository uppgRepository;
    private final CollectionPointActionRepository collectionPointActionRepository;
    private final Converter converter;
    private final WellActionRepository wellActionRepository;
    private final WellRepository wellRepository;


    public UppgActionService(UserRepository userRepository, UppgActionRepository uppgActionRepository, UppgRepository uppgRepository, CollectionPointActionRepository collectionPointActionRepository, Converter converter, WellActionRepository wellActionRepository, WellRepository wellRepository) {
        this.userRepository = userRepository;
        this.uppgActionRepository = uppgActionRepository;
        this.uppgRepository = uppgRepository;
        this.collectionPointActionRepository = collectionPointActionRepository;
        this.converter = converter;
        this.wellActionRepository = wellActionRepository;
        this.wellRepository = wellRepository;
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


    /** Auto **/

    //.....  from MODBUS
    //... coming soon
}
