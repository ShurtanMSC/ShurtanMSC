package uz.neft.controller.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.neft.service.UserService;

@org.springframework.stereotype.Controller
public class LoginLogoutController {

    @RequestMapping("/")
    public String main() {
        return "login";
    }
}
