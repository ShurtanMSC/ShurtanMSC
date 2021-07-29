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
        return userService.save(dto);
    }

    @PutMapping("/user/edit")
    public HttpEntity<?> edit(@RequestBody UserDto dto) {
        return userService.edit(dto);
    }

    @DeleteMapping("/user/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return userService.delete(id);
    }

    @GetMapping("/user/all")
    public HttpEntity<?> all() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public HttpEntity<?> userById(@PathVariable Integer id) {
        return userService.findById(id);
    }


//    Mining system CRUD

    @PostMapping("/mining/add")
    public HttpEntity<?> saveMiningSystem(@RequestBody MiningSystemDto dto) {
        return miningSystemService.save(dto);
    }

    @PutMapping("/mining/edit")
    public HttpEntity<?> editMining(@RequestBody MiningSystemDto dto) {
        return miningSystemService.edit(dto);
    }

    @DeleteMapping("/mining/delete/{id}")
    public HttpEntity<?> deleteMining(@PathVariable Integer id) {
        return miningSystemService.delete(id);
    }

    @GetMapping("/mining/all")
    public HttpEntity<?> allMinings() {
        return miningSystemService.findAll();
    }

    @GetMapping("/mining/{id}")
    public HttpEntity<?> miningById(@PathVariable Integer id) {
        return miningSystemService.findById(id);
    }


    //      UPPG CRUD

    @PostMapping("/uppg/add")
    public HttpEntity<?> saveUppg(@RequestBody UppgDto dto) {
        return uppgService.save(dto);
    }

    @PutMapping("/uppg/edit")
    public HttpEntity<?> editUppg(@RequestBody UppgDto dto) {
        return uppgService.edit(dto);
    }

    @DeleteMapping("/uppg/delete/{id}")
    public HttpEntity<?> deleteUppg(@PathVariable Integer id) {
        return uppgService.delete(id);
    }

    @GetMapping("/uppg/all")
    public HttpEntity<?> allUppgs() {
        return uppgService.findAll();
    }

    @GetMapping("/uppg/{id}")
    public HttpEntity<?> uppgById(@PathVariable Integer id) {
        return uppgService.findById(id);
    }


    //   Collection Point CRUD

    @PostMapping("/collection/add")
    public HttpEntity<?> saveCollection(@RequestBody CollectionPointDto dto) {
        return collectionPointService.save(dto);
    }

    @PutMapping("/collection/edit")
    public HttpEntity<?> editCollection(@RequestBody CollectionPointDto dto) {
        return collectionPointService.edit(dto);
    }

    @DeleteMapping("/collection/delete/{id}")
    public HttpEntity<?> deleteCollection(@PathVariable Integer id) {
        return collectionPointService.delete(id);
    }

    @GetMapping("/collection/all")
    public HttpEntity<?> allCollections() {
        return collectionPointService.findAll();
    }

    @GetMapping("/collection/{id}")
    public HttpEntity<?> collectionById(@PathVariable Integer id) {
        return collectionPointService.findById(id);
    }


    //   Well CRUD

    @PostMapping("/well/add")
    public HttpEntity<?> saveWell(@RequestBody WellDto dto) {
        return wellService.save(dto);
    }

    @PutMapping("/well/edit")
    public HttpEntity<?> editWell(@RequestBody WellDto dto) {
        return wellService.edit(dto);
    }

    @DeleteMapping("/well/delete/{id}")
    public HttpEntity<?> deleteWell(@PathVariable Integer id) {
        return wellService.delete(id);
    }

    @GetMapping("/well/all")
    public HttpEntity<?> allWells() {
        return wellService.findAll();
    }

    @GetMapping("/well/{id}")
    public HttpEntity<?> wellById(@PathVariable Integer id) {
        return wellService.findById(id);
    }

}
