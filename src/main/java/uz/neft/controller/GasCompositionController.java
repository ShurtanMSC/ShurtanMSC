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

import java.util.List;

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
    public HttpEntity<?> add(@RequestBody GasCompositionDto dto) {
        return compositionService.save(dto);
    }

    @PutMapping("edit")
    public HttpEntity<?> edit(@RequestBody GasCompositionDto dto) {
        return compositionService.edit(dto);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return compositionService.delete(id);
    }

    @GetMapping("all")
    public HttpEntity<?> all() {
        return compositionService.findAll();
    }

    @GetMapping("{id}")
    public HttpEntity<?> one(@PathVariable Integer id) {
        return compositionService.findById(id);
    }

    // Molar fractions CRUD

    @PostMapping("molar/add")
    public HttpEntity<?> addMolar(@RequestBody MiningSystemGasCompositionDto dto) {
        return compositionService.saveMSGC(dto);
    }

    @PutMapping("molar/edit")
    public HttpEntity<?> editMolar(@RequestBody MiningSystemGasCompositionDto dto) {
        return compositionService.editMSGC(dto);
    }

    @DeleteMapping("molar/delete/{id}")
    public HttpEntity<?> deleteMolar(@PathVariable Integer id) {
        return compositionService.deleteMSGC(id);
    }

    @GetMapping("molar/all")
    public HttpEntity<?> allMolar() {
        return compositionService.findAllMSGCs();
    }

    @GetMapping("molar/{id}")
    public HttpEntity<?> oneMolar(@PathVariable Integer id) {
        return compositionService.findByIdMSGC(id);
    }



    // Mining system compositions

    @GetMapping("molar/all/mining_system/{id}")
    public HttpEntity<?> allMolarByMiningSystem(@PathVariable Integer id){
        return compositionService.findAllMSGCsByMiningSystem(id);
    }

//    @PostMapping("molar/add/all/mining_system")
//    public HttpEntity<?> addMolarsByMiningSystem(@RequestBody List<MiningSystemGasCompositionDto> dtoList){
//        return compositionService.saveAllMSGCByMiningSystem(dtoList);
//    }
}
