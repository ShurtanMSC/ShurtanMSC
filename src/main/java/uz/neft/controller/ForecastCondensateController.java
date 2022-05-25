package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.dto.ForecastDto;
import uz.neft.service.ForecastCondensateService;
import uz.neft.service.MiningSystemService;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("api/forecast/condensate")
@CrossOrigin
public class ForecastCondensateController {
    @Autowired
    private MiningSystemService miningSystemService;
    @Autowired
    private ForecastCondensateService forecastCondensateService;


    @PostMapping("add/all")
    public HttpEntity<?> addAll(@RequestBody List<ForecastDto> dtoList){
        return forecastCondensateService.addAll(dtoList);
    }

    @PostMapping("add")
    public HttpEntity<?> add(@RequestParam Integer id,
                             @RequestParam int year,
                             @RequestParam Month month,@RequestParam double amount){
        return forecastCondensateService.add(id,year,month,amount);
    }

    @GetMapping("all")
    public HttpEntity<?> all(){
        return forecastCondensateService.all();
    }

    @GetMapping("all/mining_system/{id}")
    public HttpEntity<?> allByMiningSystemId(@PathVariable Integer id){
        return forecastCondensateService.allByObjectId(id);
    }

    @GetMapping("all/mining_system/{id}/{year}")
    public HttpEntity<?> allByMiningSystemIdAndYear(@PathVariable Integer id, @PathVariable int year){
        return forecastCondensateService.allByObjectAndYear(id,year);
    }

    @GetMapping("all/mining_system/{id}/{from}/{to}")
    public HttpEntity<?> allByMiningSystemIdAndYearBetween(@PathVariable Integer id, @PathVariable int from, @PathVariable int to){
        return forecastCondensateService.allByObjectAndYearBetween(id,from,to);
    }
}
