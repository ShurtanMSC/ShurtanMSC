package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.action.WellActionDto;
import uz.neft.entity.User;
import uz.neft.secret.CurrentUser;
import uz.neft.service.WellService;
import uz.neft.service.action.WellActionService;

@RestController
@RequestMapping("api/well")
@CrossOrigin
public class WellController {

    @Autowired
    private WellActionService wellActionService;

    @Autowired
    private WellService wellService;


    /**
     * Manually
     **/


    @PostMapping("manually/add/action")
    public HttpEntity<?> addAction(@CurrentUser User user,
                                   @RequestBody WellActionDto dto) {
        return wellActionService.addManually(user, dto);
    }


    @GetMapping("all")
    public HttpEntity<?> allWells() {
        return wellActionService.getWells();
    }

    @GetMapping("all/actions")
    public HttpEntity<?> wellsWithAction() {
        return wellActionService.getWellsWithAction();
    }

    @GetMapping("all/actions/collection_point/{id}")
    public HttpEntity<?> wellsWithActionByCollectionPoint(@PathVariable Integer id) {
        return wellActionService.getWellsWithActionByCollectionPoint(id);
    }

    @GetMapping("all/collection_point/{id}")
    public HttpEntity<?> wellsByCollectionPoint(@PathVariable Integer id) {
        return wellActionService.getByCollectionPoint(id);
    }


    @GetMapping("all/mining_system/{id}")
    public HttpEntity<?> getAllByMiningSystem(@PathVariable Integer id) {
        return wellActionService.getAllByMiningSystem(id);
    }

    @GetMapping("all/action/uppg/{id}")
    public HttpEntity<?> getAllActionByUppg(@PathVariable Integer id) {
        return wellActionService.getAllWithActionsByUppg(id);
    }

    @GetMapping("one/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        return wellActionService.getWell(id);
    }

    @GetMapping("one/action/{id}")
    public HttpEntity<?> getOneWithAction(@PathVariable Integer id) {
        return wellActionService.getWellWithAction(id);
    }





    /** Statistics **/

    @GetMapping("count_by_in_work")
    public HttpEntity<?> getCountByInWork() {
        return wellActionService.getCountByInWork();
    }



    /** Auto **/
    //... coming soon
}
