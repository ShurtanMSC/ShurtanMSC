package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.ElectricityDto;
import uz.neft.service.ElectricityService;

@RestController
@RequestMapping("api/electricity")
@CrossOrigin
public class ElectricityController {

    @Autowired
    private ElectricityService electricityService;




    @PostMapping("add")
    public HttpEntity<?> add(@RequestBody ElectricityDto dto){
        return electricityService.save(dto);
    }

    @PutMapping("edit")
    public HttpEntity<?> edit(@RequestBody ElectricityDto dto){
        return electricityService.edit(dto);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        return electricityService.delete(id);
    }

    @GetMapping("all")
    public HttpEntity<?> all(){
        return electricityService.allElectricity();
    }

    @GetMapping("all/last")
    public HttpEntity<?> allByLastAdded(){
        return electricityService.allFirstBy();
    }

    @GetMapping("one/{id}")
    public HttpEntity<?> one(@PathVariable Integer id){
        return electricityService.getOne(id);
    }

    @GetMapping("one/mining_system/{id}")
    public HttpEntity<?> oneByMiningSystem(@PathVariable Integer id){
        return electricityService.getOneByMiningSystem(id);
    }




}
