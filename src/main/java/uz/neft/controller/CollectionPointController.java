package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.CollectionPointDto;
import uz.neft.dto.action.CollectionPointActionDto;
import uz.neft.entity.User;
import uz.neft.secret.CurrentUser;
import uz.neft.service.CollectionPointService;
import uz.neft.service.action.CollectionPointActionService;

@RestController
@RequestMapping("api/collection_point")
@CrossOrigin
public class CollectionPointController {

    @Autowired
    private CollectionPointActionService collectionPointActionService;
    @Autowired
    private CollectionPointService collectionPointService;



    @PostMapping("add")
    public HttpEntity<?> add(@RequestBody CollectionPointDto dto) {
        return collectionPointService.save(dto);
    }

    @PutMapping("edit")
    public HttpEntity<?> edit(@RequestBody CollectionPointDto dto) {
        return collectionPointService.edit(dto);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return collectionPointService.delete(id);
    }

    @GetMapping("all")
    public HttpEntity<?> all() {
        return collectionPointService.findAll();
    }

    @GetMapping("{id}")
    public HttpEntity<?> one(@PathVariable Integer id) {
        return collectionPointService.findById(id);
    }



    /**
     * Manually
     **/
//
    @PostMapping("manually/add/action")
    public HttpEntity<?> addAction(@CurrentUser User user, @RequestBody CollectionPointActionDto dto) {
        return collectionPointActionService.addManually(user, dto);
    }





    @GetMapping("all/actions")
    public HttpEntity<?> allWithAction() {
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

    @GetMapping("all/action/mining_system/{id}")
    public HttpEntity<?> collectionsWithActionByMiningSystem(@PathVariable Integer id) {
        return collectionPointActionService.getAllWithActionsByMiningSystem(id);
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
