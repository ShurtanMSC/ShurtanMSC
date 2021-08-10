package uz.neft.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.MiningSystemForecast;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.constants.ForecastRepository;
import uz.neft.utils.Converter;

import java.time.Month;
import java.util.Optional;

@Service
public class ForecastService {
    private final ForecastRepository forecastRepository;
    private final MiningSystemRepository miningSystemRepository;
    private final Converter converter;


    public ForecastService(ForecastRepository forecastRepository, MiningSystemRepository miningSystemRepository, Converter converter) {
        this.forecastRepository = forecastRepository;
        this.miningSystemRepository = miningSystemRepository;
        this.converter = converter;
    }

    // Forecast - prognoz

    public HttpEntity<?> addForecast(Integer id, int year, Month month){
        try {
            if (id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            MiningSystemForecast forecast= MiningSystemForecast
                    .builder()
                    .miningSystem(miningSystem.get())
                    .month(month)
                    .year(year)
                    .expandGas("2131")
                    .build();
            MiningSystemForecast save = forecastRepository.save(forecast);
            return converter.apiSuccess201("Forecast created",save);
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public HttpEntity<?> allForecast() {
        try {
            return converter.apiSuccess200(forecastRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }


    public HttpEntity<?> allForecastByMiningSystemId(Integer id){
        try {
            if (id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            return converter.apiSuccess200(forecastRepository.findAllByMiningSystem(miningSystem.get()));
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public HttpEntity<?> allForecastByMiningSystemAndYearBetween(Integer mining_system_id, int from, int to) {
        try {
            if (mining_system_id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(mining_system_id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            return converter.apiSuccess200(forecastRepository.findAllByMiningSystemAndYearBetween(miningSystem.get(),from,to));
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public HttpEntity<?> allForecastByMiningSystemAndYear(Integer mining_system_id, int year){
        try {
            if (mining_system_id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(mining_system_id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            return converter.apiSuccess200(forecastRepository.findAllByMiningSystemAndYearBetween(miningSystem.get(),year,year));
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }
}
