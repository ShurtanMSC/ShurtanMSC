package uz.neft.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uz.neft.dto.*;
import uz.neft.dto.action.CollectionPointActionDto;
import uz.neft.dto.action.WellActionDto;
import uz.neft.dto.constantValue.ConstValueDto;
import uz.neft.entity.*;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.entity.action.WellAction;
import uz.neft.entity.variables.Constant;
import uz.neft.entity.variables.ConstantValue;
import uz.neft.entity.variables.GasComposition;
import uz.neft.entity.variables.MiningSystemGasComposition;
import uz.neft.payload.ApiResponse;
import uz.neft.payload.ApiResponseObject;
import uz.neft.payload.ApiResponseObjectByPageable;
import uz.neft.repository.RoleRepository;
import uz.neft.repository.UserRepository;

@Component
public class Converter {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * For responses
     **/
    public ApiResponse api(String message, boolean success) {
        return new ApiResponse(message, success);
    }

    public ApiResponse api(String message, boolean success, Object object) {
        return new ApiResponseObject(message, success, object);
    }

    public ApiResponse api(String message, boolean success, Object object, long totalElements, Integer totalPages) {
        return new ApiResponseObjectByPageable(message, success, object, totalElements, totalPages);
    }

    public ApiResponse apiError() {
        return api("Error", false);
    }

    public ApiResponse apiError(String message) {
        return api(message, false);
    }

    public ApiResponse apiError(Object object) {
        return api("Error", false, object);
    }

    public ApiResponse apiError(String message, Object object) {
        return api(message, false, object);
    }

    public ApiResponse apiSuccess() {
        return api("Success", true);
    }

    public ApiResponse apiSuccess(String message) {
        return api(message, true);
    }

    public ApiResponse apiSuccess(Object object) {
        return api("Success", true, object);
    }

    public ApiResponse apiSuccess(String message, Object object) {
        return api(message, true, object);
    }

    public ApiResponse apiSuccess(Object object, long totalElements, Integer totalPages) {
        return api("Success", true, object, totalElements, totalPages);
    }

    public ApiResponse apiSuccess(String message, Object object, long totalElements, Integer totalPages) {
        return api(message, true, object, totalElements, totalPages);
    }

    public ApiResponse apiSuccessObject(Object object) {
        return api("Success", true, object);
    }

    public ApiResponse apiSuccessObject(Object object, long totalElements, Integer totalPages) {
        return api("Success", true, object, totalElements, totalPages);
    }

    /**
     * for response with status
     **/

    protected ResponseEntity<?> helpError(HttpStatus status) {
        return ResponseEntity.status(status).body(apiError(status.name()));
    }

    protected ResponseEntity<?> helpError(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(apiError(message));
    }

    protected ResponseEntity<?> helpError(HttpStatus status, Object object) {
        return ResponseEntity.status(status).body(apiError(status.name(), object));
    }

    protected ResponseEntity<?> helpError(HttpStatus status, String message, Object object) {
        return ResponseEntity.status(status).body(apiError(message, object));
    }

    protected ResponseEntity<?> helpSuccess(HttpStatus status) {
        return ResponseEntity.status(status).body(apiSuccess(status.name()));
    }

    protected ResponseEntity<?> helpSuccess(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(apiSuccess(message));
    }

    protected ResponseEntity<?> helpSuccess(HttpStatus status, Object object) {
        return ResponseEntity.status(status).body(apiSuccess(status.name(), object));
    }

    protected ResponseEntity<?> helpSuccess(HttpStatus status, String message, Object object) {
        return ResponseEntity.status(status).body(apiSuccess(message, object));
    }

    /**
     * Error
     **/

    // Status 400
    public ResponseEntity<?> apiError400() {
        return helpError(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> apiError400(Object object) {
        return helpError(HttpStatus.BAD_REQUEST, object);
    }

    public ResponseEntity<?> apiError400(String message) {
        return helpError(HttpStatus.BAD_REQUEST, message);
    }

    public ResponseEntity<?> apiError400(String message, Object object) {
        return helpError(HttpStatus.BAD_REQUEST, message, object);
    }


    // Status 403
    public ResponseEntity<?> apiError403() {
        return helpError(HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> apiError403(Object object) {
        return helpError(HttpStatus.FORBIDDEN, object);
    }

    public ResponseEntity<?> apiError403(String message) {
        return helpError(HttpStatus.FORBIDDEN, message);
    }

    public ResponseEntity<?> apiError403(String message, Object object) {
        return helpError(HttpStatus.FORBIDDEN, message, object);
    }


    // Status 404
    public ResponseEntity<?> apiError404() {
        return helpError(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> apiError404(Object object) {
        return helpError(HttpStatus.NOT_FOUND, object);
    }

    public ResponseEntity<?> apiError404(String message) {
        return helpError(HttpStatus.NOT_FOUND, message);
    }

    public ResponseEntity<?> apiError404(String message, Object object) {
        return helpError(HttpStatus.NOT_FOUND, message, object);
    }

    // Status 409
    public ResponseEntity<?> apiError409() {
        return helpError(HttpStatus.CONFLICT);
    }

    public ResponseEntity<?> apiError409(Object object) {
        return helpError(HttpStatus.CONFLICT, object);
    }

    public ResponseEntity<?> apiError409(String message) {
        return helpError(HttpStatus.CONFLICT, message);
    }

    public ResponseEntity<?> apiError409(String message, Object object) {
        return helpError(HttpStatus.CONFLICT, message, object);
    }

    // Status 500
    public ResponseEntity<?> apiError500() {
        return helpError(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> apiError500(Object object) {
        return helpError(HttpStatus.INTERNAL_SERVER_ERROR, object);
    }

    public ResponseEntity<?> apiError500(String message) {
        return helpError(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public ResponseEntity<?> apiError500(String message, Object object) {
        return helpError(HttpStatus.INTERNAL_SERVER_ERROR, message, object);
    }

    /**
     * Success
     **/

    // Status 200
    public ResponseEntity<?> apiSuccess200() {
        return helpSuccess(HttpStatus.OK);
    }

    public ResponseEntity<?> apiSuccess200(Object object) {
        return helpSuccess(HttpStatus.OK, object);
    }

    public ResponseEntity<?> apiSuccess200(String message) {
        return helpSuccess(HttpStatus.OK, message);
    }

    public ResponseEntity<?> apiSuccess200(String message, Object object) {
        return helpSuccess(HttpStatus.OK, message, object);
    }

    // Status 201
    public ResponseEntity<?> apiSuccess201() {
        return helpSuccess(HttpStatus.CREATED);
    }

    public ResponseEntity<?> apiSuccess201(Object object) {
        return helpSuccess(HttpStatus.CREATED, object);
    }

    public ResponseEntity<?> apiSuccess201(String message) {
        return helpSuccess(HttpStatus.CREATED, message);
    }

    public ResponseEntity<?> apiSuccess201(String message, Object object) {
        return helpSuccess(HttpStatus.CREATED, message, object);
    }

    // Status 202
    public ResponseEntity<?> apiSuccess202() {
        return helpSuccess(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> apiSuccess202(Object object) {
        return helpSuccess(HttpStatus.ACCEPTED, object);
    }

    public ResponseEntity<?> apiSuccess202(String message) {
        return helpSuccess(HttpStatus.ACCEPTED, message);
    }

    public ResponseEntity<?> apiSuccess202(String message, Object object) {
        return helpSuccess(HttpStatus.ACCEPTED, message, object);
    }

    /**
     * For data transfer objects (Dto)
     **/

    public UserDto userToUserDto(User user) {
        try {
            return UserDto
                    .builder()
                    .active(user.isActive())
                    .email(user.getEmail())
                    .fio(user.getFio())
                    .id(user.getId())
                    .phone(user.getPhone())
                    .username(user.getUsername())
                    .roleId(user.getRoles().stream().findFirst().get().getId())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MiningSystemDto miningSysToMiningSysDto(MiningSystem miningSystem) {
        try {
            return MiningSystemDto
                    .builder()
                    .Id(miningSystem.getId())
                    .name(miningSystem.getName())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UppgDto uppgToUppgDto(Uppg uppg) {
        try {
            return UppgDto
                    .builder()
                    .id(uppg.getId())
                    .name(uppg.getName())
                    .miningSystemId(uppg.getMiningSystem().getId())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CollectionPointDto collectionPointToCollectionPointDto(CollectionPoint collectionPoint) {
        try {
            return CollectionPointDto
                    .builder()
                    .id(collectionPoint.getId())
                    .name(collectionPoint.getName())
                    .uppgId(collectionPoint.getUppg().getId())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public WellDto wellToWellDto(Well well) {
        try {
            return WellDto
                    .builder()
                    .id(well.getId())
                    .number(well.getNumber())
                    .collectionPointId(well.getCollectionPoint().getId())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public GasCompositionDto gasCompositionToGasCompositionDto(GasComposition composition) {
        try {
            return GasCompositionDto
                    .builder()
                    .Id(composition.getId())
                    .name(composition.getName())
                    .criticalPressure(composition.getCriticalPressure())
                    .criticalTemperature(composition.getCriticalTemperature())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ConstantDto constantToConstantDto(Constant constant) {
        try {
            return ConstantDto
                    .builder()
                    .Id(constant.getId())
                    .name(constant.getName())
                    .description(constant.getDescription())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MiningSystemGasCompositionDto miningSystemGasCompositionToMiningSystemGasCompositionDto(MiningSystemGasComposition miningSystemGasComposition) {
        try {
            return MiningSystemGasCompositionDto
                    .builder()
                    .Id(miningSystemGasComposition.getId())
                    .miningSystemId(miningSystemGasComposition.getMiningSystem().getId())
                    .gasCompositionId(miningSystemGasComposition.getGasComposition().getId())
                    .molarFraction(miningSystemGasComposition.getMolarFraction())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public WellActionDto wellActionToWellActionDto(WellAction wellAction) {
        try {
            return WellActionDto
                    .builder()
                    .wellId(wellAction.getWell().getId())
                    .pressure(wellAction.getPressure())
                    .temperature(wellAction.getTemperature())
                    .expend(wellAction.getExpend())
                    .rpl(wellAction.getRpl())
                    .status(wellAction.getStatus())
                    .date(wellAction.getCreatedAt().toString())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CollectionPointActionDto collectionPointActionToCollectionPointActionDto(CollectionPointAction collectionPointAction) {
        try {
            return CollectionPointActionDto
                    .builder()
                    .collectionPointId(collectionPointAction.getCollectionPoint().getId())
                    .expand(collectionPointAction.getExpend())
                    .pressure(collectionPointAction.getPressure())
                    .temperature(collectionPointAction.getTemperature())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ConstValueDto constantValueToConstValueDto(ConstantValue value) {
        try {
            return ConstValueDto
                    .builder()
                    .Id(value.getId())
                    .constantId(value.getConstant() == null ? null : value.getConstant().getId())
                    .mSystemId(value.getMiningSystem() == null ? null : value.getMiningSystem().getId())
                    .UppgId(value.getUppg() == null ? null : value.getUppg().getId())
                    .CpointId(value.getCollectionPoint() == null ? null : value.getCollectionPoint().getId())
                    .WellId(value.getWell() == null ? null : value.getWell().getId())
                    .value(value.getValue())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
//