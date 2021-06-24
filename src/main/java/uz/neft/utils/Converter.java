package uz.neft.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.neft.dto.UserDto;
import uz.neft.entity.User;
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
    /** For responses **/

    public ApiResponse api(String message, boolean success){
        return new ApiResponse(message,success);
    }

    public ApiResponse api(String message, boolean success,Object object){
        return new ApiResponseObject(message,success,object);
    }

    public ApiResponse api(String message, boolean success,Object object,long totalElements, Integer totalPages){
        return new ApiResponseObjectByPageable(message,success,object,totalElements,totalPages);
    }

    public ApiResponse apiError(){
        return api("Error",false);
    }

    public ApiResponse apiError(String message){
        return api(message,false);
    }

    public ApiResponse apiError(Object object){
        return api("Error",false,object);
    }

    public ApiResponse apiError(String message, Object object){
        return api(message,false,object);
    }


    public ApiResponse apiSuccess(){
        return api("Success",true);
    }

    public ApiResponse apiSuccess(String message){
        return api(message,true);
    }

    public ApiResponse apiSuccess(Object object){
        return api("Success",true,object);
    }

    public ApiResponse apiSuccess(String message, Object object){
        return api(message,true,object);
    }

    public ApiResponse apiSuccess(Object object,long totalElements, Integer totalPages){
        return api("Success",true, object, totalElements, totalPages);
    }

    public ApiResponse apiSuccess(String message, Object object,long totalElements, Integer totalPages){
        return api(message,true, object, totalElements, totalPages);
    }

    public ApiResponse apiSuccessObject(Object object){
        return api("Success",true,object);
    }

    public ApiResponse apiSuccessObject(Object object,long totalElements, Integer totalPages){
        return api("Success",true, object, totalElements, totalPages);
    }




    /** For data transfer objects (Dto) **/

//    public User userDtoToUser(UserDto dto){
//
//
//
//    }
//

}
