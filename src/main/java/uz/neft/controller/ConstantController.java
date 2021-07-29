package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.ConstantDto;
import uz.neft.payload.ApiResponse;
import uz.neft.service.ConstantService;

@RestController
@RequestMapping("api/constant")
public class ConstantController {

    private ConstantService constantService;

    @Autowired
    public ConstantController(ConstantService constantService) {
        this.constantService = constantService;
    }

    @PostMapping("add")
    public HttpEntity<?> saveConstant(@RequestBody ConstantDto dto) {
        return constantService.save(dto);
    }

    @PutMapping("edit")
    public HttpEntity<?> editConstant(@RequestBody ConstantDto dto) {
        return constantService.edit(dto);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> deleteConstant(@PathVariable Integer id) {
        return constantService.delete(id);
    }

    @GetMapping("all")
    public HttpEntity<?> allConstant() {
        return constantService.findAll();
    }

    @GetMapping("{id}")
    public HttpEntity<?> gasCompositionById(@PathVariable Integer id) {
        return constantService.findById(id);
    }
}
