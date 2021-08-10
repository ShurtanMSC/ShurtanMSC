package uz.neft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.neft.service.ForecastGasService;
import uz.neft.service.MiningSystemService;

import java.time.Month;

@RestController
@RequestMapping("api/forecast")
@CrossOrigin
public class ForecastController {
    @Autowired
    private MiningSystemService miningSystemService;
    @Autowired
    private ForecastGasService forecastGasService;



    @PostMapping("add")
    public HttpEntity<?> add_forecast(@RequestParam Integer id,
                                      @RequestParam int year,
                                      @RequestParam Month month){
        return forecastGasService.addForecast(id,year,month);
    }

    @GetMapping("all")
    public HttpEntity<?> all_forecast(){
        return forecastGasService.allForecast();
    }

    @GetMapping("all/mining_system/{id}")
    public HttpEntity<?> all_forecast_by_mining_system_id(@PathVariable Integer id){
        return forecastGasService.allForecastByObjectId(id);
    }

    @GetMapping("all/mining_system/{id}/{year}")
    public HttpEntity<?> all_forecast_by_mining_system_id_and_year(@PathVariable Integer id, @PathVariable int year){
        return forecastGasService.allForecastByObjectAndYear(id,year);
    }

    @GetMapping("all/mining_system/{id}/{from}/{to}")
    public HttpEntity<?> all_forecast_by_mining_system_id_and_year_between(@PathVariable Integer id, @PathVariable int from, @PathVariable int to){
        return forecastGasService.allForecastByObjectAndYearBetween(id,from,to);
    }
}
