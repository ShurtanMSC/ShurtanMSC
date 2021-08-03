package uz.neft.service.action;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.CollectionPointDto;
import uz.neft.dto.action.CollectionPointActionDto;
import uz.neft.dto.action.CollectionPointAndActionsDto;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Uppg;
import uz.neft.entity.User;
import uz.neft.entity.Well;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.entity.action.WellAction;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.UppgRepository;
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
    private final UppgRepository uppgRepository;


    public CollectionPointActionService(UserRepository userRepository, CollectionPointRepository collectionPointRepository, CollectionPointActionRepository collectionPointActionRepository, Converter converter, WellActionRepository wellActionRepository, WellRepository wellRepository, UppgRepository uppgRepository) {
        this.userRepository = userRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.collectionPointActionRepository = collectionPointActionRepository;
        this.converter = converter;
        this.wellActionRepository = wellActionRepository;
        this.wellRepository = wellRepository;
        this.uppgRepository = uppgRepository;
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

            List<CollectionPointAndActionsDto> collect = all.stream().map(item -> {
                Optional<CollectionPoint> byId = collectionPointRepository.findById(item.getId());
                CollectionPointAction collectionPointAction = collectionPointActionRepository.findFirstByCollectionPoint(byId.get());

                if (collectionPointAction == null) return null;
                else {
                    CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(byId.get());
                    CollectionPointActionDto collectionPointActionDto = converter.collectionPointActionToCollectionPointActionDto(collectionPointAction);
                    CollectionPointAndActionsDto dto = CollectionPointAndActionsDto
                            .builder()
                            .collectionPointDto(collectionPointDto)
                            .collectionPointActionDto(collectionPointActionDto)
                            .build();
                    return dto;
                }
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
            if (!byId.isPresent()) return converter.apiError404("collection pint not found");

            CollectionPointAction collectionPointAction = collectionPointActionRepository.findFirstByCollectionPoint(byId.get());

            CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(byId.get());
            CollectionPointActionDto collectionPointActionDto = converter.collectionPointActionToCollectionPointActionDto(collectionPointAction);
            CollectionPointAndActionsDto dto = CollectionPointAndActionsDto
                    .builder()
                    .collectionPointDto(collectionPointDto)
                    .collectionPointActionDto(collectionPointActionDto)
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


}
