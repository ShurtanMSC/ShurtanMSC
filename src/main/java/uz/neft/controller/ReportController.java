package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.neft.service.ReportService;

import java.io.IOException;

@RestController
@RequestMapping("api/report")
@CrossOrigin
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/test")
    public HttpEntity<?> report() throws IOException {
        reportService.generateReport(1);
        return ResponseEntity.ok("Ok");
    }

}
