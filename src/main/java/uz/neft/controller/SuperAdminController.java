package uz.neft.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.*;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.OpcServer;
import uz.neft.repository.RoleRepository;
import uz.neft.service.*;
import uz.neft.service.action.MiningSystemActionService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/admin")
@CrossOrigin
public class SuperAdminController {

    private final UserService userService;
    private final MiningSystemService miningSystemService;
    private final MiningSystemActionService miningSystemActionService;
    private final UppgService uppgService;
    private final CollectionPointService collectionPointService;
    private final WellService wellService;
    private final RoleRepository roleRepository;
    private final OpcServerService opcServerService;

    public SuperAdminController(UserService userService, MiningSystemService miningSystemService, MiningSystemActionService miningSystemActionService, UppgService uppgService, CollectionPointService collectionPointService, WellService wellService, RoleRepository roleRepository, OpcServerService opcServerService) {
        this.userService = userService;
        this.miningSystemService = miningSystemService;
        this.miningSystemActionService = miningSystemActionService;
        this.uppgService = uppgService;
        this.collectionPointService = collectionPointService;
        this.wellService = wellService;
        this.roleRepository = roleRepository;
        this.opcServerService = opcServerService;
    }


    /**
     * Roles
     **/
    @GetMapping("/role/all")
    public HttpEntity<?> getRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    /**
     * User qo'shish
     **/

    @PostMapping("/user/add")
    public HttpEntity<?> save(@RequestBody UserDto dto) {
        System.out.println(dto);
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

    @GetMapping("/mining/allActions/{miningId}")
    public HttpEntity<?> allWithActionsByMining(@PathVariable Integer miningId) {
        return miningSystemActionService.allWithActionsByMiningSystem(miningId);
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

    @PostMapping("/collection_point/add/{uppgId}/{opcId}")
    public HttpEntity<?> saveCollection(@PathVariable Integer uppgId,
                                        @PathVariable Integer opcId,
                                        @RequestBody CollectionPoint collectionPoint) {
        return collectionPointService.saveCollectionPointAdmin(collectionPoint, uppgId, opcId);
    }

    @PutMapping("/collection_point/edit/{uppgId}/{opcId}")
    public HttpEntity<?> editCollection(@PathVariable Integer uppgId,
                                        @PathVariable Integer opcId,
                                        @RequestBody CollectionPoint collectionPoint) {
        return collectionPointService.editCPAdmin(collectionPoint,opcId,uppgId);
    }

    @DeleteMapping("/collection_point/delete/{id}")
    public HttpEntity<?> deleteCollection(@PathVariable Integer id) {
        return collectionPointService.delete(id);
    }

    @GetMapping("/collection_point/all")
    public HttpEntity<?> allCollections() {
        return collectionPointService.findAll();
    }

    @GetMapping("/collection_point/all/{uppgId}")
    public HttpEntity<?> allCollectionsByUppgId(@PathVariable Integer uppgId) {
        return collectionPointService.findAllByUppgId(uppgId);
    }

    @GetMapping("/collection_point/{id}")
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


    //   Opc Server CRUD

    @PostMapping("/opc_server/add")
    public HttpEntity<?> saveOpcServer(@Valid @RequestBody OpcServer opcServer, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }

        return opcServerService.save(opcServer);
    }

    @PutMapping("/opc_server/edit")
    public HttpEntity<?> editOpcServer(@Valid @RequestBody OpcServer opcServer, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }

        return opcServerService.edit(opcServer);
    }

    @DeleteMapping("/opc_server/delete/{id}")
    public HttpEntity<?> deleteOpcServer(@PathVariable Integer id) {
        return opcServerService.delete(id);
    }

    @GetMapping("/opc_server/all")
    public HttpEntity<?> allOpcServers() {
        return opcServerService.findAll();
    }

    @GetMapping("/opc_server/{id}")
    public HttpEntity<?> opcServerById(@PathVariable Integer id) {
        return opcServerService.findById(id);
    }

}
