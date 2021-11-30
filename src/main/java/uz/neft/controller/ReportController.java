package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.format.annotation.DateTimeFormat;
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
        OutputStream outputStream = reportService.generateTechReport(1, name);

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
        OutputStream outputStream = reportService.generateTechReport(1, name);

        String filePath = name+".xlsx";
        InputStream inputStream = new FileInputStream(new File(filePath));
        String fileName = URLEncoder.encode(name, "UTF-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename\""+ fileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(Files.size(Paths.get(filePath)))
                .body(new FileUrlResource(filePath));

    }

    @GetMapping("/interval")
    public HttpEntity<?> reportInterval(@RequestParam Integer mining_system_id, @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date start, @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date end){
//        return reportService.all(mining_system_id,start,end);
        return reportService.techReport(mining_system_id,start,end);
    }


    @GetMapping("/production/interval")
    public HttpEntity<?> reportProductionInterval(@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date start, @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date end){
        return reportService.productionReport(start,end);
    }

    @GetMapping("/production/excel/{name}")
    public HttpEntity<?> reportProductionExcel(HttpServletResponse response, @PathVariable String name,@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date start, @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date end) throws Exception {
        Date date=new Date();
//        String name=String.valueOf(date.getTime());
        OutputStream outputStream = reportService.generateProductionReport("production - "+name, start, end);

        String filePath = "production - "+name+".xlsx";
        InputStream inputStream = new FileInputStream(new File(filePath));
        String fileName = URLEncoder.encode(name, "UTF-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename\""+ fileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(Files.size(Paths.get(filePath)))
                .body(new FileUrlResource(filePath));
    }

    @GetMapping("/staff/interval")
    public HttpEntity<?> reportStaffInterval(@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date start, @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date end){
        return reportService.staffReport(start,end);
    }

    @GetMapping("/staff/excel/{name}")
    public HttpEntity<?> reportStaffExcel(HttpServletResponse response, @PathVariable String name,@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date start, @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date end) throws Exception {
        Date date=new Date();
//        String name=String.valueOf(date.getTime());
        OutputStream outputStream = reportService.generateStaffReport("staff - "+name, start, end);

        String filePath = "staff - "+name+".xlsx";
        InputStream inputStream = new FileInputStream(new File(filePath));
        String fileName = URLEncoder.encode(name, "UTF-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename\""+ fileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(Files.size(Paths.get(filePath)))
                .body(new FileUrlResource(filePath));
    }


    @GetMapping("/electricity/interval")
    public HttpEntity<?> reportElectricityInterval(@RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date start, @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date end){
        return reportService.electricityReport(start,end);
    }

    @GetMapping("/electricity/excel/{name}")
    public HttpEntity<?> reportElectricityExcel(HttpServletResponse response, @PathVariable String name,@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date start, @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date end) throws Exception {
        Date date=new Date();
//        String name=String.valueOf(date.getTime());
        OutputStream outputStream = reportService.generateElectricityReport("electricity - "+name, start, end);

        String filePath = "electricity - "+name+".xlsx";
        InputStream inputStream = new FileInputStream(new File(filePath));
        String fileName = URLEncoder.encode(name, "UTF-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename\""+ fileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(Files.size(Paths.get(filePath)))
                .body(new FileUrlResource(filePath));
    }


}
