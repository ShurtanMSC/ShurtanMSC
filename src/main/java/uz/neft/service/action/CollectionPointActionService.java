package uz.neft.service.action;

import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.action.CollectionPointActionDto;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.User;
import uz.neft.entity.Well;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.entity.action.WellAction;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.UserRepository;
import uz.neft.repository.WellRepository;
import uz.neft.repository.action.CollectionPointActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.utils.Converter;

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


    public CollectionPointActionService(UserRepository userRepository, CollectionPointRepository collectionPointRepository, CollectionPointActionRepository collectionPointActionRepository, Converter converter, WellActionRepository wellActionRepository, WellRepository wellRepository) {
        this.userRepository = userRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.collectionPointActionRepository = collectionPointActionRepository;
        this.converter = converter;
        this.wellActionRepository = wellActionRepository;
        this.wellRepository = wellRepository;
    }


    /**
     * Manually
     **/
    public HttpEntity<?> addManually(User user, CollectionPointActionDto dto) {


        if (dto.getCollectionPointId()==null) return converter.apiError400("Collection Point id is null");
        Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(dto.getCollectionPointId());
        if (!collectionPoint.isPresent()) return converter.apiError400("Collection Point not found");

        List<Well> allByCollectionPoint = wellRepository.findAllByCollectionPoint(collectionPoint.get());

        double D_sp = 0;

        for (Well well : allByCollectionPoint) {
            Optional<WellAction> wellAction = wellActionRepository.findFirstByWell(well);
            if (wellAction.isPresent()){
                D_sp+=wellAction.get().getExpend();
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


    /** Auto **/

    //..... from MODBUS
}
