package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.GasCompositionDto;
import uz.neft.entity.variables.GasComposition;
import uz.neft.payload.ApiResponse;
import uz.neft.service.GasCompositionService;

@RestController
@RequestMapping("api/gas_composition")
public class GasCompositionController {

    GasCompositionService compositionService;

    @Autowired
    public GasCompositionController(GasCompositionService compositionService) {
        this.compositionService = compositionService;
    }

    @PostMapping("add")
    public HttpEntity<?> saveGasComposition(@RequestBody GasCompositionDto dto) {
        ApiResponse save = compositionService.save(dto);
        return ResponseEntity.ok(save);
    }

    @PutMapping("edit")
    public HttpEntity<?> editGasComposition(@RequestBody GasCompositionDto dto) {
        ApiResponse edit = compositionService.edit(dto);
        return ResponseEntity.ok(edit);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> deleteGasComposition(@PathVariable Integer id) {
        ApiResponse delete = compositionService.delete(id);
        return ResponseEntity.ok(delete);
    }

    @GetMapping("all")
    public HttpEntity<?> allGasComposition() {
        ApiResponse all = compositionService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("{id}")
    public HttpEntity<?> gasCompositionById(@PathVariable Integer id) {
        ApiResponse byId = compositionService.findById(id);
        return ResponseEntity.ok(byId);
    }

}
