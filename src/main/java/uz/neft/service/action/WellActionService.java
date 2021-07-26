package uz.neft.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.dto.action.WellActionDto;
import uz.neft.entity.User;
import uz.neft.entity.Well;
import uz.neft.entity.action.WellAction;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.WellRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.service.Calculator;
import uz.neft.utils.Converter;

import java.util.Optional;

@Service
public class WellActionService {
    @Autowired
    private WellActionRepository wellActionRepository;
    @Autowired
    private WellRepository wellRepository;
    @Autowired
    private Converter converter;

    @Autowired
    private Calculator calculator;

    /** Manually **/
    public ApiResponse addManually(User user, WellActionDto dto) {

        Optional<Well> well=wellRepository.findById(dto.getWellId());
        if (user==null) return converter.apiError();
        if (!well.isPresent()) return converter.apiError("Quduq topilmadi!");


        try {
            WellAction wellAction=WellAction
                    .builder()
                    .user(user)
                    .temperature(dto.getTemperature())
                    .pressure(dto.getPressure())
                    .status(dto.getStatus())
                    .rpl(dto.getRpl())
                    .well(well.get())
                    .build();

            WellAction save = wellActionRepository.save(wellAction);
            return converter.apiSuccess(save);
        }catch (Exception e){
            return converter.apiError();
        }


    }
}
