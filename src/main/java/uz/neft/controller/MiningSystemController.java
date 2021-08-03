package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.action.WellActionDto;
import uz.neft.entity.User;
import uz.neft.secret.CurrentUser;
import uz.neft.service.action.WellActionService;

@RestController
@RequestMapping("api/well")
public class MiningSystemController {



    /**
     * Manually
     **/

//
//    @GetMapping("all")
//    public HttpEntity<?> allWells() {
//        return wellActionService.getWells();
//    }
//
//    @GetMapping("all_with_actions")
//    public HttpEntity<?> wellsWithAction() {
//        return wellActionService.getWellsWithAction();
//    }
//
//    @GetMapping("all_with_actions_by_collection_point/{id}")
//    public HttpEntity<?> wellsWithActionByCollectionPoint(@PathVariable Integer id) {
//        return wellActionService.getWellsWithActionByCollectionPoint(id);
//    }
//
//    @GetMapping("all_by_collection_point/{id}")
//    public HttpEntity<?> wellsByCollectionPoint(@PathVariable Integer id) {
//        return wellActionService.getByCollectionPoint(id);
//    }
//
//    @GetMapping("one/{id}")
//    public HttpEntity<?> getOne(@PathVariable Integer id) {
//        return wellActionService.getWell(id);
//    }
//
//    @GetMapping("with_action/{id}")
//    public HttpEntity<?> getOneWithAction(@PathVariable Integer id) {
//        return wellActionService.getWellWithAction(id);
//    }
//
//


    /** Auto **/
    //... coming soon
}
