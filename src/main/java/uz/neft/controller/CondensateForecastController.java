package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.service.ForecastCondensateService;
import uz.neft.service.ForecastGasService;
import uz.neft.service.MiningSystemService;

import java.time.Month;

@RestController
@RequestMapping("api/forecast/condensate")
@CrossOrigin
public class CondensateForecastController {
    @Autowired
    private MiningSystemService miningSystemService;
    @Autowired
    private ForecastCondensateService forecastCondensateService;



    @PostMapping("add")
    public HttpEntity<?> addForecast(@RequestParam Integer id,
                                      @RequestParam int year,
                                      @RequestParam Month month){
        return forecastCondensateService.addForecast(id,year,month);
    }

    @GetMapping("all")
    public HttpEntity<?> allForecast(){
        return forecastCondensateService.allForecast();
    }

    @GetMapping("all/mining_system/{id}")
    public HttpEntity<?> allForecast_by_mining_system_id(@PathVariable Integer id){
        return forecastCondensateService.allForecastByObjectId(id);
    }

    @GetMapping("all/mining_system/{id}/{year}")
    public HttpEntity<?> allForecast_by_mining_system_id_and_year(@PathVariable Integer id, @PathVariable int year){
        return forecastCondensateService.allForecastByObjectAndYear(id,year);
    }

    @GetMapping("all/mining_system/{id}/{from}/{to}")
    public HttpEntity<?> allForecast_by_mining_system_id_and_year_between(@PathVariable Integer id, @PathVariable int from, @PathVariable int to){
        return forecastCondensateService.allForecastByObjectAndYearBetween(id,from,to);
    }
}
