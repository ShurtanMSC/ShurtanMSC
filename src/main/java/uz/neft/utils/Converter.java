package uz.neft.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.neft.dto.*;
import uz.neft.entity.*;
import uz.neft.entity.variables.Constant;
import uz.neft.entity.variables.GasComposition;
import uz.neft.entity.variables.MiningSystemGasComposition;
import uz.neft.payload.ApiResponse;
import uz.neft.payload.ApiResponseObject;
import uz.neft.payload.ApiResponseObjectByPageable;
import uz.neft.repository.RoleRepository;
import uz.neft.repository.UserRepository;


import java.util.stream.Collectors;

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
                    .miningSystem(miningSysToMiningSysDto(uppg.getMiningSystem()))
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
                    .uppgDto(uppgToUppgDto(collectionPoint.getUppg()))
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
                    .collectionPointDto(collectionPointToCollectionPointDto(well.getCollectionPoint()))
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

}
