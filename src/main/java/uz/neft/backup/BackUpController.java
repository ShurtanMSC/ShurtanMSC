package uz.neft.backup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/backup")
public class BackUpController {

    @Autowired
    private Test test;
    @GetMapping("")
    public HttpEntity<?> backUp() throws Exception {
        BackupMiningSystem backupMiningSystem = new BackupMiningSystem();

        return ResponseEntity.ok(test.backup());
    }


}
