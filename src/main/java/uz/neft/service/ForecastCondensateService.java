package uz.neft.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.constants.ForecastGasRepository;
import uz.neft.utils.Converter;

import java.time.Month;

@Service
public class ForecastCondensateService implements ForecastMethodInterface{

    private final ForecastGasRepository forecastGasRepository;
    private final MiningSystemRepository miningSystemRepository;
    private final Converter converter;

    public ForecastCondensateService(ForecastGasRepository forecastGasRepository, MiningSystemRepository miningSystemRepository, Converter converter) {
        this.forecastGasRepository = forecastGasRepository;
        this.miningSystemRepository = miningSystemRepository;
        this.converter = converter;
    }

    @Override
    public HttpEntity<?> addForecast(Integer id, int year, Month month) {
        return null;
    }

    @Override
    public HttpEntity<?> allForecast() {
        return null;
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
