package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import uz.neft.service.document.ReportService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@RestController
@RequestMapping("api/report")
@CrossOrigin
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/test")
    public HttpEntity<?> report(HttpServletResponse response) throws Exception {
        Date date=new Date();
        String name=String.valueOf(date.getTime());
        OutputStream outputStream = reportService.generateReport(1, name);

        String filePath = name+".xlsx";
        InputStream inputStream = new FileInputStream(new File(filePath));
        String fileName = URLEncoder.encode(name, "UTF-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename\""+ fileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(Files.size(Paths.get(filePath)))
                .body(new FileUrlResource(filePath));

    }


    @GetMapping("/test/{name}")
    public HttpEntity<?> report(HttpServletResponse response, @PathVariable String name) throws Exception {
        Date date=new Date();
//        String name=String.valueOf(date.getTime());
        OutputStream outputStream = reportService.generateReport(1, name);

        String filePath = name+".xlsx";
        InputStream inputStream = new FileInputStream(new File(filePath));
        String fileName = URLEncoder.encode(name, "UTF-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename\""+ fileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(Files.size(Paths.get(filePath)))
                .body(new FileUrlResource(filePath));

    }



}
