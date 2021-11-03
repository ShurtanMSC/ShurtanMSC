package uz.neft.dto.fake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/fake")
public class FakeController {
    @Autowired
    private FakeService fakeService;

    @GetMapping("/all")
    public HttpEntity<?> all(){
        return ResponseEntity.ok(fakeService.all());
    }

}
