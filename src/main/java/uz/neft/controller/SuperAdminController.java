package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.UserDto;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.UserRepository;
import uz.neft.service.UserService;
import uz.neft.utils.Converter;

@RestController
@RequestMapping("api/admin/")
@CrossOrigin
public class SuperAdminController {

    private final UserService userService;

    @Autowired
    public SuperAdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * User qo'shish
     **/

    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody UserDto dto) {
        ApiResponse save = userService.save(dto);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/edit")
    public HttpEntity<?> edit(@RequestBody UserDto dto) {
        ApiResponse edit = userService.edit(dto);
        return ResponseEntity.ok(edit);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse delete = userService.delete(id);
        return ResponseEntity.ok(delete);
    }

    @GetMapping("/users")
    public HttpEntity<?> all() {
        ApiResponse all = userService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/user/{id}")
    public HttpEntity<?> userById(@PathVariable Integer id) {
        ApiResponse byId = userService.findById(id);
        return ResponseEntity.ok(byId);
    }


}
