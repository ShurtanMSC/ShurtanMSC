package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.neft.entity.User;
import uz.neft.payload.ResToken;
import uz.neft.payload.SignIn;
import uz.neft.secret.CurrentUser;
import uz.neft.service.AuthService;
import uz.neft.utils.Converter;


@RestController
@Controller
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private Converter converter;
    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody SignIn signIn){
        ResToken resToken=authService.signIn(signIn);
        return ResponseEntity.status(resToken!=null?200:401).body(resToken);
    }


    @GetMapping("/test")
    public HttpEntity<?> test(){
        return ResponseEntity.ok(converter.apiSuccess());
    }

    @GetMapping("/me")
    public HttpEntity<?> me(@CurrentUser User user){
        if (user!=null){
            return converter.apiSuccess200(converter.userToUserDto(user));
        }else return null;
    }

}
