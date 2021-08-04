package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.GasCompositionDto;
import uz.neft.dto.MiningSystemGasCompositionDto;
import uz.neft.entity.variables.GasComposition;
import uz.neft.payload.ApiResponse;
import uz.neft.service.GasCompositionService;

@RestController
@RequestMapping("api/gas_composition")
@CrossOrigin
public class GasCompositionController {

    GasCompositionService compositionService;

    @Autowired
    public GasCompositionController(GasCompositionService compositionService) {
        this.compositionService = compositionService;
    }

    @PostMapping("add")
    public HttpEntity<?> saveGasComposition(@RequestBody GasCompositionDto dto) {
        return compositionService.save(dto);
    }

    @PutMapping("edit")
    public HttpEntity<?> editGasComposition(@RequestBody GasCompositionDto dto) {
        return compositionService.edit(dto);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> deleteGasComposition(@PathVariable Integer id) {
        return compositionService.delete(id);
    }

    @GetMapping("all")
    public HttpEntity<?> allGasComposition() {
        return compositionService.findAll();
    }

    @GetMapping("{id}")
    public HttpEntity<?> gasCompositionById(@PathVariable Integer id) {
        return compositionService.findById(id);
    }

    // Molar fractions CRUD

    @PostMapping("molar/add")
    public HttpEntity<?> saveMiningSystemGasComposition(@RequestBody MiningSystemGasCompositionDto dto) {
        return compositionService.saveMSGC(dto);
    }

    @PutMapping("molar/edit")
    public HttpEntity<?> editMiningSystemGasComposition(@RequestBody MiningSystemGasCompositionDto dto) {
        return compositionService.editMSGC(dto);
    }

    @DeleteMapping("molar/delete/{id}")
    public HttpEntity<?> deleteMiningSystemGasComposition(@PathVariable Integer id) {
        return compositionService.deleteMSGC(id);
    }

    @GetMapping("molar/all")
    public HttpEntity<?> allMiningSystemGasComposition() {
        return compositionService.findAllMSGCs();
    }

    @GetMapping("molar/{id}")
    public HttpEntity<?> gasCMiningSystemGasComposition(@PathVariable Integer id) {
        return compositionService.findByIdMSGC(id);
    }

}
