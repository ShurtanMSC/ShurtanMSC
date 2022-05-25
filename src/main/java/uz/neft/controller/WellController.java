package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.WellDto;
import uz.neft.dto.action.WellActionDto;
import uz.neft.entity.User;
import uz.neft.secret.CurrentUser;
import uz.neft.service.WellService;
import uz.neft.service.action.WellActionService;

import java.util.Optional;

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

    @PostMapping("add")
    public HttpEntity<?> saveWell(@RequestBody WellDto dto) {
        return wellService.save(dto);
    }

    @PutMapping("edit")
    public HttpEntity<?> editWell(@RequestBody WellDto dto) {
        return wellService.edit(dto);
    }
    @PutMapping("edit/action")
    public HttpEntity<?> editWellAction(@RequestBody WellActionDto dto) {
        return wellActionService.editAction(dto);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> deleteWell(@PathVariable Integer id) {
        return wellService.delete(id);
    }

    @DeleteMapping("delete/action/{id}")
    public HttpEntity<?> deleteWellAction(@PathVariable Long id) {
        return wellActionService.deleteWellAction(id);
    }

    @GetMapping("all")
    public HttpEntity<?> allWells() {
        return wellService.findAll();
    }

    @GetMapping("all/collection_point/{id}")
    public HttpEntity<?> wellsByCollectionPoint(@PathVariable Integer id) {
        return wellActionService.getByCollectionPoint(id);
    }

    @GetMapping("{id}")
    public HttpEntity<?> wellById(@PathVariable Integer id) {
        return wellService.findById(id);
    }

    @GetMapping("all/actions")
    public HttpEntity<?> wellsWithAction() {
        return wellActionService.getWellsWithAction();
    }

    @GetMapping("all/actions/collection_point/{id}")
    public HttpEntity<?> wellsWithActionByCollectionPoint(@PathVariable Integer id) {
        return wellActionService.getWellsWithActionByCollectionPoint(id);
    }




    @GetMapping("all/mining_system/{id}")
    public HttpEntity<?> getAllByMiningSystem(@PathVariable Integer id) {
        return wellActionService.getAllByMiningSystem(id);
    }

    @GetMapping("all/action/mining_system/{id}")
    public HttpEntity<?> getAllActionByMiningSystem(@PathVariable Integer id) {
        return wellActionService.getAllWithActionByMiningSystem(id);
    }

    @GetMapping("all/action/uppg/{id}")
    public HttpEntity<?> getAllActionByUppg(@PathVariable Integer id) {
        return wellActionService.getAllWithActionsByUppg(id);
    }

    @GetMapping("actions/{wellId}")
    public HttpEntity<?> getAllActionsByCollectionPointId(@PathVariable Integer wellId,
                                                          @RequestParam(value = "page", required = false, defaultValue = "0") Optional<Integer> page,
                                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Optional<Integer> pageSize,
                                                          @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") Optional<String> sortBy
    ) {
        return wellActionService.getAllActionsByWell(wellId, page, pageSize, sortBy);
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


    @GetMapping("stat/status")
    public HttpEntity<?> getStatByStatus(){
        return wellActionService.getStatByStatus();
    }


    /** Auto **/
    //... coming soon
}
