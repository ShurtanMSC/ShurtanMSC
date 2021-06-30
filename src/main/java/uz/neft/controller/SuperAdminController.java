package uz.neft.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.*;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.RoleRepository;
import uz.neft.service.*;

@RestController
@RequestMapping("api/admin")
@CrossOrigin
public class SuperAdminController {

    private final UserService userService;
    private final MiningSystemService miningSystemService;
    private final UppgService uppgService;
    private final CollectionPointService collectionPointService;
    private final WellService wellService;
    private final RoleRepository roleRepository;

    public SuperAdminController(UserService userService, MiningSystemService miningSystemService, UppgService uppgService, CollectionPointService collectionPointService, WellService wellService, RoleRepository roleRepository) {
        this.userService = userService;
        this.miningSystemService = miningSystemService;
        this.uppgService = uppgService;
        this.collectionPointService = collectionPointService;
        this.wellService = wellService;
        this.roleRepository = roleRepository;
    }


    /** Roles **/
    public HttpEntity<?> getRoles(){
        return ResponseEntity.ok(roleRepository.findAll());
    }

    /** User qo'shish  **/

    @PostMapping("/user/add")
    public HttpEntity<?> save(@RequestBody UserDto dto) {
        ApiResponse save = userService.save(dto);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/user/edit")
    public HttpEntity<?> edit(@RequestBody UserDto dto) {
        ApiResponse edit = userService.edit(dto);
        return ResponseEntity.ok(edit);
    }

    @DeleteMapping("/user/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse delete = userService.delete(id);
        return ResponseEntity.ok(delete);
    }

    @GetMapping("/user/all")
    public HttpEntity<?> all() {
        ApiResponse all = userService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/user/{id}")
    public HttpEntity<?> userById(@PathVariable Integer id) {
        ApiResponse byId = userService.findById(id);
        return ResponseEntity.ok(byId);
    }


//    Mining system CRUD

    @PostMapping("/mining/add")
    public HttpEntity<?> saveMiningSystem(@RequestBody MiningSystemDto dto) {
        ApiResponse save = miningSystemService.save(dto);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/mining/edit")
    public HttpEntity<?> editMining(@RequestBody MiningSystemDto dto) {
        ApiResponse edit = miningSystemService.edit(dto);
        return ResponseEntity.ok(edit);
    }

    @DeleteMapping("/mining/delete/{id}")
    public HttpEntity<?> deleteMining(@PathVariable Integer id) {
        ApiResponse delete = miningSystemService.delete(id);
        return ResponseEntity.ok(delete);
    }

    @GetMapping("/mining/all")
    public HttpEntity<?> allMinings() {
        ApiResponse all = miningSystemService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/mining/{id}")
    public HttpEntity<?> miningById(@PathVariable Integer id) {
        ApiResponse byId = miningSystemService.findById(id);
        return ResponseEntity.ok(byId);
    }


    //      UPPG CRUD

    @PostMapping("/uppg/add")
    public HttpEntity<?> saveUppg(@RequestBody UppgDto dto) {
        ApiResponse save = uppgService.save(dto);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/uppg/edit")
    public HttpEntity<?> editUppg(@RequestBody UppgDto dto) {
        ApiResponse edit = uppgService.edit(dto);
        return ResponseEntity.ok(edit);
    }

    @DeleteMapping("/uppg/delete/{id}")
    public HttpEntity<?> deleteUppg(@PathVariable Integer id) {
        ApiResponse delete = uppgService.delete(id);
        return ResponseEntity.ok(delete);
    }

    @GetMapping("/uppg/all")
    public HttpEntity<?> allUppgs() {
        ApiResponse all = uppgService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/uppg/{id}")
    public HttpEntity<?> uppgById(@PathVariable Integer id) {
        ApiResponse byId = uppgService.findById(id);
        return ResponseEntity.ok(byId);
    }


    //   Collection Point CRUD

    @PostMapping("/collection/add")
    public HttpEntity<?> saveCollection(@RequestBody CollectionPointDto dto) {
        ApiResponse save = collectionPointService.save(dto);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/collection/edit")
    public HttpEntity<?> editCollection(@RequestBody CollectionPointDto dto) {
        ApiResponse edit = collectionPointService.edit(dto);
        return ResponseEntity.ok(edit);
    }

    @DeleteMapping("/collection/delete/{id}")
    public HttpEntity<?> deleteCollection(@PathVariable Integer id) {
        ApiResponse delete = collectionPointService.delete(id);
        return ResponseEntity.ok(delete);
    }

    @GetMapping("/collection/all")
    public HttpEntity<?> allCollections() {
        ApiResponse all = collectionPointService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/collection/{id}")
    public HttpEntity<?> collectionById(@PathVariable Integer id) {
        ApiResponse byId = collectionPointService.findById(id);
        return ResponseEntity.ok(byId);
    }


    //   Well CRUD

    @PostMapping("/well/add")
    public HttpEntity<?> saveWell(@RequestBody WellDto dto) {
        ApiResponse save = wellService.save(dto);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/well/edit")
    public HttpEntity<?> editWell(@RequestBody WellDto dto) {
        ApiResponse edit = wellService.edit(dto);
        return ResponseEntity.ok(edit);
    }

    @DeleteMapping("/well/delete/{id}")
    public HttpEntity<?> deleteWell(@PathVariable Integer id) {
        ApiResponse delete = wellService.delete(id);
        return ResponseEntity.ok(delete);
    }

    @GetMapping("/well/all")
    public HttpEntity<?> allWells() {
        ApiResponse all = wellService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/well/{id}")
    public HttpEntity<?> wellById(@PathVariable Integer id) {
        ApiResponse byId = wellService.findById(id);
        return ResponseEntity.ok(byId);
    }

}
