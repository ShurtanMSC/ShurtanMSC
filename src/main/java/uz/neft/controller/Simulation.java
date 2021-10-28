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

    @PostMapping("collection_point")
    public String[] test(@RequestBody Simulate simulate){
        String[] a=new String[3];
        a[0]= String.valueOf(new SecureRandom().nextFloat()*50);
        a[1]="Good";
        a[2]= String.valueOf(new Date());
        return a;
    }

    @PostMapping("collection_point/temperature")
    public String[] testT(@RequestBody Simulate simulate){
        String[] a=new String[3];
        a[0]= String.valueOf(new SecureRandom().nextFloat()*(60.0-50.0)+50.0);
        a[1]="Good";
        a[2]= String.valueOf(new Date());
        return a;
    }

    @PostMapping("collection_point/pressure")
    public String[] testP(@RequestBody Simulate simulate){
        String[] a=new String[3];
        a[0]= String.valueOf(new SecureRandom().nextFloat()*(26.0-22.0)+22.0);
        a[1]="Good";
        a[2]= String.valueOf(new Date());
        return a;
    }

}
