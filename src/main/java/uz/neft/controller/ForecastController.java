package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.entity.MiningSystem;
import uz.neft.service.ForecastService;
import uz.neft.service.MiningSystemService;

import java.time.Month;

@RestController
@RequestMapping("api/forecast")
@CrossOrigin
public class ForecastController {
    @Autowired
    private MiningSystemService miningSystemService;
    @Autowired
    private ForecastService forecastService;



    @PostMapping("add")
    public HttpEntity<?> add_forecast(@RequestParam Integer id,
                                      @RequestParam int year,
                                      @RequestParam Month month){
        return forecastService.addForecast(id,year,month);
    }

    @GetMapping("all")
    public HttpEntity<?> all_forecast(){
        return forecastService.allForecast();
    }

    @GetMapping("all/mining_system/{id}")
    public HttpEntity<?> all_forecast_by_mining_system_id(@PathVariable Integer id){
        return forecastService.allForecastByMiningSystemId(id);
    }

    @GetMapping("all/mining_system/{id}/{year}")
    public HttpEntity<?> all_forecast_by_mining_system_id_and_year(@PathVariable Integer id, @PathVariable int year){
        return forecastService.allForecastByMiningSystemAndYear(id,year);
    }

    @GetMapping("all/mining_system/{id}/{from}/{to}")
    public HttpEntity<?> all_forecast_by_mining_system_id_and_year_between(@PathVariable Integer id, @PathVariable int from, @PathVariable int to){
        return forecastService.allForecastByMiningSystemAndYearBetween(id,from,to);
    }
}
