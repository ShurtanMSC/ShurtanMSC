package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.action.UppgActionDto;
import uz.neft.entity.User;
import uz.neft.secret.CurrentUser;
import uz.neft.service.action.UppgActionService;

@RestController
@RequestMapping("api/uppg")
@CrossOrigin
public class UppgController {

    @Autowired
    UppgActionService uppgActionService;

    /**
     * Manually
     **/
    @PostMapping("manually/add/action")
    public HttpEntity<?> addAction(@CurrentUser User user,
                                   @RequestBody UppgActionDto dto) {
        return uppgActionService.addManually(user, dto);
    }


    @GetMapping("all")
    public HttpEntity<?> allUppgs() {
        return uppgActionService.getUppgs();
    }

    @GetMapping("all/actions")
    public HttpEntity<?> uppgsWithActionBy() {
        return uppgActionService.getUppgsWithAction();
    }

    @GetMapping("all/actions/mining_system/{id}")
    public HttpEntity<?> uppgsWithActionByMiningSystem(@PathVariable Integer id) {
        return uppgActionService.getUppgsWithActionByMiningSystem(id);
    }

    @GetMapping("all/mining_system/{id}")
    public HttpEntity<?> uppgsByMiningSystem(@PathVariable Integer id) {
        return uppgActionService.getByMiningSystem(id);
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
