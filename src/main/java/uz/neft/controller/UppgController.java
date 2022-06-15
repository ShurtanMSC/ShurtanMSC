package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.UppgDto;
import uz.neft.dto.action.UppgActionDto;
import uz.neft.entity.User;
import uz.neft.secret.CurrentUser;
import uz.neft.service.UppgService;
import uz.neft.service.action.UppgActionService;

import java.util.Optional;

@RestController
@RequestMapping("api/uppg")
@CrossOrigin
public class UppgController {

    @Autowired
    private UppgActionService uppgActionService;
    @Autowired
    private UppgService uppgService;

    /**
     * Manually
     **/

    @PostMapping("add/action")
    public HttpEntity<?> addAction(@CurrentUser User user,
                                   @RequestBody UppgActionDto dto) {
        return uppgActionService.addManually(user, dto);
    }


    @PostMapping("add")
    public HttpEntity<?> saveUppg(@RequestBody UppgDto dto) {
        return uppgService.save(dto);
    }

    @PutMapping("edit")
    public HttpEntity<?> editUppg(@RequestBody UppgDto dto) {
        return uppgService.edit(dto);
    }

    @PutMapping("edit/action")
    public HttpEntity<?> editUppgAction(@RequestBody UppgActionDto dto) {
        return uppgActionService.editAction(dto);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> deleteUppg(@PathVariable Integer id) {
        return uppgService.delete(id);
    }

    @DeleteMapping("delete/action/{id}")
    public HttpEntity<?> deleteUppgAction(@PathVariable Long id) {
        return uppgActionService.deleteUppgAction(id);
    }

    @GetMapping("all")
    public HttpEntity<?> allUppgs() {
        return uppgService.findAll();
    }

    @GetMapping("all/mining_system/{id}")
    public HttpEntity<?> uppgsByMiningSystem(@PathVariable Integer id) {
        return uppgActionService.getByMiningSystem(id);
    }

//    @GetMapping("actions/{uppgId}")
//    public HttpEntity<?> uppgActionsByUppgId(@PathVariable Integer uppgId) {
//        return uppgActionService.getUppgActionsByUppgId(uppgId);
//    }

    @GetMapping("actions/{uppgId}")
    public HttpEntity<?> uppgActionsByUppgId(@PathVariable Integer uppgId,
                                             @RequestParam(value = "page", required = false, defaultValue = "0") Optional<Integer> page,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Optional<Integer> pageSize,
                                             @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") Optional<String> sortBy
    ) {
        return uppgActionService.getUppgActionsByUppgId(uppgId,page,pageSize,sortBy);
    }

    @GetMapping("{id}")
    public HttpEntity<?> uppgById(@PathVariable Integer id) {
        return uppgService.findById(id);
    }




    @GetMapping("all/actions")
    public HttpEntity<?> uppgsWithAction() {
        return uppgActionService.getUppgsWithAction();
    }

    @GetMapping("all/actions/mining_system/{id}")
    public HttpEntity<?> uppgsWithActionByMiningSystem(@PathVariable Integer id) {
        return uppgActionService.getUppgsWithActionByMiningSystem(id);
    }

    @GetMapping("one/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        return uppgActionService.getUppg(id);
    }

    @GetMapping("one/action/{id}")
    public HttpEntity<?> getOneWithAction(@PathVariable Integer id) {
        return uppgActionService.getUppgWithAction(id);
    }


    /** Auto **/

    // ...... from MODBUS
    //... coming soon


}
