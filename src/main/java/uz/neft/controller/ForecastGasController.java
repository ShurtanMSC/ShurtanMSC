package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.ForecastDto;
import uz.neft.service.ForecastGasService;
import uz.neft.service.MiningSystemService;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("api/forecast/gas")
@CrossOrigin
public class ForecastGasController {
    @Autowired
    private MiningSystemService miningSystemService;
    @Autowired
    private ForecastGasService forecastGasService;


    @PostMapping("add/all")
    public HttpEntity<?> addAll(@RequestBody List<ForecastDto> dtoList){
        return forecastGasService.addAll(dtoList);
    }

    @PostMapping("add")
    public HttpEntity<?> add(@RequestParam Integer id,
                             @RequestParam int year,
                             @RequestParam Month month, double amount){
        return forecastGasService.add(id,year,month,amount);
    }

    @GetMapping("all")
    public HttpEntity<?> all(){
        return forecastGasService.all();
    }

    @GetMapping("all/mining_system/{id}")
    public HttpEntity<?> allByMiningSystemId(@PathVariable Integer id){
        return forecastGasService.allByObjectId(id);
    }

    @GetMapping("all/mining_system/{id}/{year}")
    public HttpEntity<?> allByMiningSystemIdAndYear(@PathVariable Integer id, @PathVariable int year){
        return forecastGasService.allByObjectAndYear(id,year);
    }

    @GetMapping("all/mining_system/{id}/{from}/{to}")
    public HttpEntity<?> allByMiningSystemIdAndYearBetween(@PathVariable Integer id, @PathVariable int from, @PathVariable int to){
        return forecastGasService.allByObjectAndYearBetween(id,from,to);
    }
}
