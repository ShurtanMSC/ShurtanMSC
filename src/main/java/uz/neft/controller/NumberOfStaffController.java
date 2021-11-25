package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.StaffDto;
import uz.neft.service.NumberOfStaffService;

@RestController
@CrossOrigin
@RequestMapping("api/staff/number")
public class NumberOfStaffController {

    @Autowired
    private NumberOfStaffService numberOfStaffService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody StaffDto dto){
        return numberOfStaffService.add(dto);
    }

    @GetMapping("/one/{id}")
    public HttpEntity<?> one(@PathVariable int id){
        return numberOfStaffService.one(id);
    }

    @GetMapping("/all")
    public HttpEntity<?> all(){
        return numberOfStaffService.all();
    }
    
}
