package uz.neft.controller.mvc;

import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class LoginLogoutController {

    @RequestMapping("/")
    public String main() {
        return "login";
    }
}
