package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.MiningSystemDto;
import uz.neft.dto.action.MiningSystemActionDto;
import uz.neft.service.MiningSystemService;
import uz.neft.service.action.MiningSystemActionService;

import java.util.Optional;

@RestController
@RequestMapping("api/mining_system")
@CrossOrigin
public class MiningSystemController {

    @Autowired
    private MiningSystemService miningSystemService;
    @Autowired
    private MiningSystemActionService miningSystemActionService;


    @PostMapping("add")
    public HttpEntity<?> add(@RequestBody MiningSystemDto dto) {
        return miningSystemService.save(dto);
    }

    @PostMapping("add/action")
    public HttpEntity<?> addAction(@RequestBody MiningSystemActionDto dto) {
        return miningSystemService.saveAction(dto);
    }

    @PutMapping("edit")
    public HttpEntity<?> edit(@RequestBody MiningSystemDto dto) {
        return miningSystemService.edit(dto);
    }

    @PutMapping("edit/action")
    public HttpEntity<?> editAction(@RequestBody MiningSystemActionDto dto) {
        return miningSystemService.editAction(dto);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return miningSystemService.delete(id);
    }

    @DeleteMapping("delete/action/{id}")
    public HttpEntity<?> deleteAction(@PathVariable Long id) {
        return miningSystemService.deleteAction(id);
    }

    @GetMapping("all")
    public HttpEntity<?> all() {
        return miningSystemService.findAll();
    }

    @GetMapping("{id}")
    public HttpEntity<?> one(@PathVariable Integer id) {
        return miningSystemService.findById(id);
    }

    @GetMapping("one/action/{id}")
    public HttpEntity<?> miningSystemWithAction(@PathVariable Integer id){
        return miningSystemActionService.findByIdWithAction(id);
    }

    @GetMapping("all/actions")
    public HttpEntity<?> allWithAction(){
        return miningSystemActionService.allWithActions();
    }

    @GetMapping("all/actions/{miningSystemId}")
    public HttpEntity<?> allActionsByMiningSystemId(@PathVariable Integer miningSystemId,
                                             @RequestParam(value = "page", required = false, defaultValue = "0") Optional<Integer> page,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Optional<Integer> pageSize,
                                             @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") Optional<String> sortBy
    ) {
        return miningSystemActionService.allWithActionsByMiningSystem(miningSystemId,page,pageSize,sortBy);
    }


    /**
     * Manually
     **/



    /** Auto **/
    //... coming soon
}
