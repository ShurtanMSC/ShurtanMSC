package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.service.ForecastGasService;
import uz.neft.service.MiningSystemService;

import java.time.Month;

@RestController
@RequestMapping("api/forecast/gas")
@CrossOrigin
public class ForecastGasController {
    @Autowired
    private MiningSystemService miningSystemService;
    @Autowired
    private ForecastGasService forecastGasService;



    @PostMapping("add")
    public HttpEntity<?> add(@RequestParam Integer id,
                                      @RequestParam int year,
                                      @RequestParam Month month){
        return forecastGasService.addForecast(id,year,month);
    }

    @GetMapping("all")
    public HttpEntity<?> all(){
        return forecastGasService.allForecast();
    }

    @GetMapping("all/mining_system/{id}")
    public HttpEntity<?> allByMiningSystemId(@PathVariable Integer id){
        return forecastGasService.allForecastByObjectId(id);
    }

    @GetMapping("all/mining_system/{id}/{year}")
    public HttpEntity<?> allByMiningSystemIdAndYear(@PathVariable Integer id, @PathVariable int year){
        return forecastGasService.allForecastByObjectAndYear(id,year);
    }

    @GetMapping("all/mining_system/{id}/{from}/{to}")
    public HttpEntity<?> allByMiningSystemIdAndYearBetween(@PathVariable Integer id, @PathVariable int from, @PathVariable int to){
        return forecastGasService.allForecastByObjectAndYearBetween(id,from,to);
    }
}
