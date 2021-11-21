package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.ConstantDto;
import uz.neft.dto.ConstantDto2;
import uz.neft.dto.constantValue.ConstantValueDto;
import uz.neft.service.ConstantService;

@RestController
@RequestMapping("api/constant")
@CrossOrigin
public class ConstantController {

    private final ConstantService constantService;

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
    public HttpEntity<?> one(@PathVariable Integer id) {
        return constantService.findById(id);
    }

    // GET ALL CONSTANTS------------------------------------------------------------------
    @GetMapping("mining/all/{miningId}")
    public HttpEntity<?> allConstantsByMiningId(@PathVariable Integer miningId) {
        return constantService.findAllMiningSysConst(miningId);
    }
    @GetMapping("uppg/all/{uppgId}")
    public HttpEntity<?> allConstantsByUppgId(@PathVariable Integer uppgId) {
        return constantService.findAllUppgConst(uppgId);
    }
    @GetMapping("collection_point/all/{collectionId}")
    public HttpEntity<?> allConstantsByCollectionPointId(@PathVariable Integer collectionId) {
        return constantService.findAllCollectionPointConst(collectionId);
    }
    @GetMapping("well/all/{wellId}")
    public HttpEntity<?> allConstantsByWellId(@PathVariable Integer wellId) {
        return constantService.findAllWellConst(wellId);
    }

    // SAVE CONSTANTS ------------------------------------------------------------------
    @PostMapping("mining/save/{miningId}")
    public HttpEntity<?> saveMiningConstant(@RequestBody ConstantDto2 dto,
                                    @PathVariable Integer miningId) {
        return constantService.saveMiningConstant(dto,miningId);
    }
    @PostMapping("uppg/save/{uppgId}")
    public HttpEntity<?> saveUppgConstant(@RequestBody ConstantDto2 dto,
                                    @PathVariable Integer uppgId) {
        return constantService.saveUppgConstant(dto,uppgId);
    }
    @PostMapping("collection_point/save/{collectionPointId}")
    public HttpEntity<?> saveCollectionPointConstant(@RequestBody ConstantDto2 dto,
                                    @PathVariable Integer collectionPointId) {
        return constantService.saveCollectionPointConstant(dto,collectionPointId);
    }
    @PostMapping("well/save/{wellId}")
    public HttpEntity<?> saveWellConstant(@RequestBody ConstantDto2 dto,
                                    @PathVariable Integer wellId) {
        return constantService.saveWellConstant(dto,wellId);
    }

    // DELETE CONSTANTS------------------------------------------------------------------
    @DeleteMapping("mining/delete/{id}/{constantOf}")
    public HttpEntity<?> deleteConstant(@PathVariable Integer id,
                                        @PathVariable String constantOf) {
        return constantService.deleteConstant(id,constantOf);
    }

    @DeleteMapping("uppg/delete/{id}/{constantOf}")
    public HttpEntity<?> deleteUppgConstant(@PathVariable Integer id,
                                            @PathVariable String constantOf) {
        return constantService.deleteConstant(id, constantOf);
    }
    @DeleteMapping("collection_point/delete/{id}/{constantOf}")
    public HttpEntity<?> deleteCollectionConstant(@PathVariable Integer id,
                                            @PathVariable String constantOf) {
        return constantService.deleteConstant(id, constantOf);
    }
    @DeleteMapping("well/delete/{id}/{constantOf}")
    public HttpEntity<?> deleteWellConstant(@PathVariable Integer id,
                                            @PathVariable String constantOf) {
        return constantService.deleteConstant(id, constantOf);
    }

    // EDIT CONSTANTS------------------------------------------------------------------
    @PutMapping("mining/edit/{miningId}")
    public HttpEntity<?> editMiningConstant(@RequestBody ConstantDto2 dto,
                              @PathVariable Integer miningId) {
        return constantService.editMiningConstant(dto, miningId);
    }
    @PutMapping("uppg/edit/{uppgId}")
    public HttpEntity<?> editUppgConstant(@RequestBody ConstantDto2 dto,
                              @PathVariable Integer uppgId) {
        return constantService.editUppgConstant(dto, uppgId);
    }
    @PutMapping("collection_point/edit/{collectionPointId}")
    public HttpEntity<?> editCollectionPointConstant(@RequestBody ConstantDto2 dto,
                              @PathVariable Integer collectionPointId) {
        return constantService.editCollectionPointConstant(dto, collectionPointId);
    }
    @PutMapping("well/edit/{wellId}")
    public HttpEntity<?> editWellConstant(@RequestBody ConstantDto2 dto,
                              @PathVariable Integer wellId) {
        return constantService.editWellConstant(dto, wellId);
    }

    //constant value CRUD

    @PostMapping("value/add")
    public HttpEntity<?> saveValue(@RequestBody ConstantValueDto dto) {
        return constantService.saveValue(dto);
    }

    @PutMapping("value/edit")
    public HttpEntity<?> editValue(@RequestBody ConstantValueDto dto) {
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
