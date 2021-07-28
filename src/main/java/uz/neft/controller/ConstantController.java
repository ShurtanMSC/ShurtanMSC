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
        ApiResponse save = constantService.save(dto);
        return ResponseEntity.ok(save);
    }

    @PutMapping("edit")
    public HttpEntity<?> editConstant(@RequestBody ConstantDto dto) {
        ApiResponse edit = constantService.edit(dto);
        return ResponseEntity.ok(edit);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> deleteConstant(@PathVariable Integer id) {
        ApiResponse delete = constantService.delete(id);
        return ResponseEntity.ok(delete);
    }

    @GetMapping("all")
    public HttpEntity<?> allConstant() {
        ApiResponse all = constantService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("{id}")
    public HttpEntity<?> gasCompositionById(@PathVariable Integer id) {
        ApiResponse byId = constantService.findById(id);
        return ResponseEntity.ok(byId);
    }
}
