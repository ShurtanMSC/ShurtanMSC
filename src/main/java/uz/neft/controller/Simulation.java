package uz.neft.controller;

import org.springframework.web.bind.annotation.*;
import uz.neft.payload.Simulate;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("api/simulate")
@CrossOrigin
public class Simulation {

    @PostMapping("test")
    public String[] test(@RequestBody Simulate simulate){
        String[] a=new String[3];
        a[0]= String.valueOf(new SecureRandom().nextFloat()*10000);
        a[1]="Good";
        a[2]= String.valueOf(new Date());
        return a;
    }

}
