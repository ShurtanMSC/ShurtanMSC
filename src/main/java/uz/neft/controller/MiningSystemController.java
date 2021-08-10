package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.action.WellActionDto;
import uz.neft.entity.User;
import uz.neft.secret.CurrentUser;
import uz.neft.service.MiningSystemService;
import uz.neft.service.action.MiningSystemActionService;
import uz.neft.service.action.WellActionService;

import java.time.Month;

@RestController
@RequestMapping("api/mining_system")
@CrossOrigin
public class MiningSystemController {

    @Autowired
    private MiningSystemService miningSystemService;
    @Autowired
    private MiningSystemActionService miningSystemActionService;


    @GetMapping("all")
    public HttpEntity<?> all() {
        return miningSystemService.findAll();
    }

    @GetMapping("one/{id}")
    public HttpEntity<?> one(@PathVariable Integer id) {
        return miningSystemService.findById(id);
    }

    @GetMapping("one/action/{id}")
    public HttpEntity<?> with_action(@PathVariable Integer id){
        return miningSystemActionService.findByIdWithAction(id);
    }

    @GetMapping("all/actions")
    public HttpEntity<?> all_with_actions(){
        return miningSystemActionService.allWithActions();
    }



    @PostMapping("add/forecast")
    public HttpEntity<?> add_forecast(@RequestParam Integer id,
                                      @RequestParam int year,
                                      @RequestParam Month month){
        return miningSystemActionService.addForecast(id,year,month);
    }

    @GetMapping("all/forecast")
    public HttpEntity<?> all_forecast(){
        return miningSystemActionService.allForecast();
    }


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
