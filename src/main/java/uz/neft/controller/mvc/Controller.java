package uz.neft.controller.mvc;

import org.springframework.http.HttpEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.UserDto;
import uz.neft.service.UserService;

@org.springframework.stereotype.Controller
@RequestMapping("admin")
public class Controller {
    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String main() {
        return "index";
    }

    /**
     * Mining
     **/


//    USER CRUD
    @GetMapping("users")
    public String users() {

        return "mining/users";
    }

    @GetMapping("mining_system")
    public String mining_system() {
        return "mining/mining_system";
    }

//    @GetMapping("mining_system/action")
//    public String miningSystemAction() {
//        return "mining/action/mining_system_action";
//    }

    //    UPPG CRUD
    @GetMapping("uppg")
    public String uppg() {
        return "mining/uppg";
    }

    //    COLLECTION POINT CRUD
    @GetMapping("collection_point")
    public String collection_point() {
        return "mining/collection_point";
    }


    @GetMapping("well")
    public String well() {
        return "mining/well";
    }

    @GetMapping("system_variables")
    public String system_variables() {
        return "mining/system_variables";
    }

}
