package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.MiningSystemDto;
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


    @PostMapping("add")
    public HttpEntity<?> add(@RequestBody MiningSystemDto dto) {
        return miningSystemService.save(dto);
    }

    @PutMapping("edit")
    public HttpEntity<?> edit(@RequestBody MiningSystemDto dto) {
        return miningSystemService.edit(dto);
    }

    @DeleteMapping("delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return miningSystemService.delete(id);
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




    /**
     * Manually
     **/



    /** Auto **/
    //... coming soon
}
