package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.action.CollectionPointActionDto;
import uz.neft.entity.User;
import uz.neft.secret.CurrentUser;
import uz.neft.service.action.CollectionPointActionService;

@RestController
@RequestMapping("api/collection_point")
@CrossOrigin
public class CollectionPointController {

    @Autowired
    private CollectionPointActionService collectionPointActionService;

    /**
     * Manually
     **/
//
    @PostMapping("manually/add/action")
    public HttpEntity<?> addAction(@CurrentUser User user, @RequestBody CollectionPointActionDto dto) {
        return collectionPointActionService.addManually(user, dto);
    }

    @GetMapping("all")
    public HttpEntity<?> allSP() {
        return collectionPointActionService.getCollectionPoints();
    }

    @GetMapping("all/actions")
    public HttpEntity<?> all() {
        return collectionPointActionService.getCollectionPointsWithActions();
    }

    @GetMapping("all/uppg/{id}")
    public HttpEntity<?> getAllByUppg(@PathVariable Integer id) {
        return collectionPointActionService.getByUppg(id);
    }

    @GetMapping("all/mining_system/{id}")
    public HttpEntity<?> getAllByMiningSystem(@PathVariable Integer id) {
        return collectionPointActionService.getAllByMiningSystem(id);
    }

    @GetMapping("one/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        return collectionPointActionService.getCollectionPoint(id);
    }

    @GetMapping("one/action/{id}")
    public HttpEntity<?> getOneWithAction(@PathVariable Integer id) {
        return collectionPointActionService.getCollectionPointWithAction(id);
    }


    /** Auto **/

    // ...... from MODBUS
    //... coming soon


}
