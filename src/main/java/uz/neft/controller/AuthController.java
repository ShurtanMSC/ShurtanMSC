package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.neft.payload.ResToken;
import uz.neft.payload.SignIn;
import uz.neft.service.AuthService;


@RestController
@Controller
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody SignIn signIn){
        ResToken resToken=authService.signIn(signIn);
        return ResponseEntity.status(resToken!=null?200:401).body(resToken);
    }


}
