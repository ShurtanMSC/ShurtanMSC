package uz.neft.controller.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("admin")
public class Controller {

    @GetMapping("")
    public String main(){
        return "index";
    }







    /** Mining **/

    @GetMapping("users")
    public String users(){
        return "mining/users";
    }

    @GetMapping("uppg")
    public String uppg(){
        return "mining/uppg";
    }

    @GetMapping("collection_point")
    public String collection_point(){
        return "mining/collection_point";
    }


    @GetMapping("well")
    public String well(){
        return "mining/well";
    }

    @GetMapping("system_variables")
    public String system_variables(){
        return "mining/system_variables";
    }

}
