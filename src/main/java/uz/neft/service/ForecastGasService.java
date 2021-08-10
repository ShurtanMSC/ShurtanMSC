package uz.neft.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.ForecastGas;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.constants.ForecastGasRepository;
import uz.neft.utils.Converter;

import java.time.Month;
import java.util.Optional;

@Service
public class ForecastGasService implements ForecastMethodInterface{
    private final ForecastGasRepository forecastGasRepository;
    private final MiningSystemRepository miningSystemRepository;
    private final Converter converter;


    public ForecastGasService(ForecastGasRepository forecastGasRepository, MiningSystemRepository miningSystemRepository, Converter converter) {
        this.forecastGasRepository = forecastGasRepository;
        this.miningSystemRepository = miningSystemRepository;
        this.converter = converter;
    }

    // Forecast - prognoz

    @Override
    public HttpEntity<?> addForecast(Integer id, int year, Month month){
        try {
            if (id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            ForecastGas forecast= ForecastGas
                    .builder()
                    .miningSystem(miningSystem.get())
                    .month(month)
                    .year(year)
                    .expand("2131")
                    .build();
            ForecastGas save = forecastGasRepository.save(forecast);
            return converter.apiSuccess201("Forecast created",save);
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    @Override
    public HttpEntity<?> allForecast() {
        try {
            return converter.apiSuccess200(forecastGasRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }


    @Override
    public HttpEntity<?> allForecastByObjectId(Integer id){
        try {
            if (id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            return converter.apiSuccess200(forecastGasRepository.findAllByMiningSystem(miningSystem.get()));
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    @Override
    public HttpEntity<?> allForecastByObjectAndYearBetween(Integer mining_system_id, int from, int to) {
        try {
            if (mining_system_id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(mining_system_id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            return converter.apiSuccess200(forecastGasRepository.findAllByMiningSystemAndYearBetween(miningSystem.get(),from,to));
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    @Override
    public HttpEntity<?> allForecastByObjectAndYear(Integer mining_system_id, int year){
        try {
            if (mining_system_id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(mining_system_id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            return converter.apiSuccess200(forecastGasRepository.findAllByMiningSystemAndYearBetween(miningSystem.get(),year,year));
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }
}
