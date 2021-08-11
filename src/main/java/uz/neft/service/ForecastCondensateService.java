package uz.neft.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.entity.ForecastCondensate;
import uz.neft.entity.ForecastGas;
import uz.neft.entity.MiningSystem;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.constants.ForecastCondensateRepository;
import uz.neft.repository.constants.ForecastGasRepository;
import uz.neft.utils.Converter;

import java.time.Month;
import java.util.Optional;

@Service
public class ForecastCondensateService implements ForecastMethodInterface{

    private final ForecastCondensateRepository forecastCondensateRepository;
    private final MiningSystemRepository miningSystemRepository;
    private final Converter converter;

    public ForecastCondensateService(ForecastCondensateRepository forecastCondensateRepository, MiningSystemRepository miningSystemRepository, Converter converter) {
        this.forecastCondensateRepository = forecastCondensateRepository;
        this.miningSystemRepository = miningSystemRepository;
        this.converter = converter;
    }

    @Override
    public HttpEntity<?> addForecast(Integer id, int year, Month month) {
        try {
            if (id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            ForecastCondensate forecast= ForecastCondensate
                    .builder()
                    .miningSystem(miningSystem.get())
                    .month(month)
                    .year(year)
                    .expand("2131")
                    .build();
            ForecastCondensate save = forecastCondensateRepository.save(forecast);
            return converter.apiSuccess201("Forecast created",save);
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    @Override
    public HttpEntity<?> allForecast() {
        try {
            return converter.apiSuccess200(forecastCondensateRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    @Override
    public HttpEntity<?> allForecastByObjectId(Integer id) {
        return null;
    }

    @Override
    public HttpEntity<?> allForecastByObjectAndYearBetween(Integer id, int from, int to) {
        return null;
    }

    @Override
    public HttpEntity<?> allForecastByObjectAndYear(Integer id, int year) {
        return null;
    }
}
