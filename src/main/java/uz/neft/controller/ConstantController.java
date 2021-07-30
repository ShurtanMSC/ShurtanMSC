package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.ConstantDto;
import uz.neft.dto.constantValue.ConstValueDto;
import uz.neft.service.ConstantService;

@RestController
@RequestMapping("api/constant")
public class ConstantController {

    private ConstantService constantService;

    @Autowired
    public ConstantController(ConstantService constantService) {
        this.constantService = constantService;
    }

    //  constant CRUD

    @PostMapping("add")
    public HttpEntity<?> save(@RequestBody ConstantDto dto) {
        return constantService.save(dto);
    }

    @PutMapping("edit")
    public HttpEntity<?> edit(@RequestBody ConstantDto dto) {
        return constantService.edit(dto);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return constantService.delete(id);
    }

    @GetMapping("all")
    public HttpEntity<?> all() {
        return constantService.findAll();
    }

    @GetMapping("{id}")
    public HttpEntity<?> byId(@PathVariable Integer id) {
        return constantService.findById(id);
    }


      //constant value CRUD

    @PostMapping("value/add")
    public HttpEntity<?> saveValue(@RequestBody ConstValueDto dto) {
        return constantService.saveValue(dto);
    }

    @PutMapping("value/edit")
    public HttpEntity<?> editValue(@RequestBody ConstValueDto dto) {
        return constantService.editValue(dto);
    }

    @DeleteMapping("value/delete/{id}")
    public HttpEntity<?> deleteValue(@PathVariable Integer id) {
        return constantService.deleteValue(id);
    }

    @GetMapping("value/all")
    public HttpEntity<?> allValues() {
        return constantService.allValues();
    }

    @GetMapping("value/{id}")
    public HttpEntity<?> byIdValue(@PathVariable Integer id) {
        return constantService.byIdValue(id);
    }

}
