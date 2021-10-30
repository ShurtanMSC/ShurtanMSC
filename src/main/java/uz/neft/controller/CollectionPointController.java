package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.CollectionPointDto;
import uz.neft.dto.action.CollectionPointActionDto;
import uz.neft.dto.special.CollectionPointAndWells;
import uz.neft.entity.User;
import uz.neft.secret.CurrentUser;
import uz.neft.service.CollectionPointService;
import uz.neft.service.action.CollectionPointActionService;
import uz.neft.utils.Converter;

import java.util.Optional;

@RestController
@RequestMapping("api/collection_point")
@CrossOrigin
public class CollectionPointController {
    @Autowired
    private Converter converter;

    @Autowired
    private CollectionPointActionService collectionPointActionService;
    @Autowired
    private CollectionPointService collectionPointService;

    @PostMapping("add")
    public HttpEntity<?> add(@RequestBody CollectionPointDto dto) {
        System.out.println(dto);
        return collectionPointService.save(dto);
    }

    @PutMapping("edit")
    public HttpEntity<?> edit(@RequestBody CollectionPointDto dto) {
        return collectionPointService.edit(dto);
    }

    @GetMapping ("active/{id}/{isActive}")
    public HttpEntity<?> changeActive(@PathVariable Integer id, @PathVariable Boolean isActive) {
        if (id == null) return converter.apiError400("CollectionPoint Id is null");
        if (isActive == null) return converter.apiError400("checked info is null");
        return collectionPointService.changeActive(id, isActive);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return collectionPointService.delete(id);
    }

    @DeleteMapping("delete/action/{id}")
    public HttpEntity<?> deleteCollectionPointAction(@PathVariable Long id) {
        return collectionPointActionService.deleteAction(id);
    }

    @GetMapping("all")
    public HttpEntity<?> all() {
        return collectionPointService.findAll();
    }

    @GetMapping("all/uppg/{id}")
    public HttpEntity<?> getAllByUppg(@PathVariable Integer id) {
        return collectionPointActionService.getByUppg(id);
    }

    @GetMapping("{id}")
    public HttpEntity<?> one(@PathVariable Integer id) {
        return collectionPointService.findById(id);
    }


    /**
     * Manually
     **/


    @PostMapping("manually/add/action")
    public HttpEntity<?> addAction(@CurrentUser User user, @RequestBody CollectionPointActionDto dto) {
        return collectionPointActionService.addManually(user, dto);
    }

    @PostMapping("manually/add/special")
    public HttpEntity<?> addSpecial(@CurrentUser User user, @RequestBody CollectionPointAndWells collectionPointAndWells) {
        return collectionPointActionService.addSpecial(user, collectionPointAndWells);
    }

    @GetMapping("all/actions")
    public HttpEntity<?> allWithAction() {
        return collectionPointActionService.getCollectionPointsWithActions();
    }

    @GetMapping("all/mining_system/{id}")
    public HttpEntity<?> getAllByMiningSystem(@PathVariable Integer id) {
        return collectionPointActionService.getAllByMiningSystem(id);
    }

    @GetMapping("actions/{collectionPointId}")
    public HttpEntity<?> getAllActionsByCollectionPointId(@PathVariable Integer collectionPointId,
                                                          @RequestParam(value = "page", required = false, defaultValue = "0") Optional<Integer> page,
                                                          @RequestParam(value = "pageSaze", required = false, defaultValue = "10") Optional<Integer> pageSaze,
                                                          @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") Optional<String> sortBy
    ) {
        return collectionPointActionService.getAllActionsByCollectionPoint(collectionPointId, page, pageSaze, sortBy);
    }

    @PutMapping("action/edit")
    public HttpEntity<?> editAction(@RequestBody CollectionPointActionDto dto) {
        return collectionPointActionService.editAction(dto);
    }

    @GetMapping("all/action/mining_system/{id}")
    public HttpEntity<?> collectionsWithActionByMiningSystem(@PathVariable Integer id) {
        return collectionPointActionService.getAllWithActionsByMiningSystem(id);
    }

    @GetMapping("one/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        return collectionPointActionService.getCollectionPoint(id);
    }

    @PutMapping("active/{id}/{checked}")
    public HttpEntity<?> getOne(@PathVariable Integer id, @PathVariable String checked) {
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
