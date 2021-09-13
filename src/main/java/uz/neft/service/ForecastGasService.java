package uz.neft.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.ForecastDto;
import uz.neft.entity.ForecastCondensate;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.ForecastGas;
import uz.neft.payload.ApiResponse;
import uz.neft.payload.ApiResponseObject;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.constants.ForecastGasRepository;
import uz.neft.utils.Converter;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
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
    public ApiResponse add(ForecastDto dto) {
        try {
            if (dto.getMining_system_id()==null) return converter.apiError("Mining system id is null!");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMining_system_id());
            if (!miningSystem.isPresent()) return converter.apiError("Mining system not found");
            if (dto.getYear()<=1970) return converter.apiError("Year is invalid!");
            if (dto.getAmount()<=0) return converter.apiError("Amount is invalid!");
            if (dto.getMonth()==null) return converter.apiError("Month is null!");
            ForecastGas gas=new ForecastGas();
            Optional<ForecastGas> forecastGas = forecastGasRepository.findByMiningSystemAndYearAndMonth(miningSystem.get(), dto.getYear(), dto.getMonth());
            if (forecastGas.isPresent()){
                gas=forecastGas.get();
            }
            gas.trans(dto);
            gas.setMiningSystem(miningSystem.get());
//            ForecastCondensate save = forecastCondensateRepository.save(condensate);
            return converter.apiSuccess(gas);
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError();
        }
    }

    @Override
    public HttpEntity<?> addDto(ForecastDto dto) {
        try {
            if (dto.getMining_system_id()==null) return converter.apiError400("Mining system id is null!");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMining_system_id());
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            if (dto.getYear()<=1970) return converter.apiError400("Year is invalid!");
            if (dto.getAmount()<=0) return converter.apiError400("Amount is invalid!");
            if (dto.getMonth()==null) return converter.apiError400("Month is null!");
            ForecastGas gas=new ForecastGas();
            Optional<ForecastGas> forecastGas = forecastGasRepository.findByMiningSystemAndYearAndMonth(miningSystem.get(), dto.getYear(), dto.getMonth());
            if (forecastGas.isPresent()){
                gas=forecastGas.get();
            }
            gas.trans(dto);
            gas.setMiningSystem(miningSystem.get());
            ForecastGas save = forecastGasRepository.save(gas);
            return converter.apiSuccess200(save);
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    @Override
    public HttpEntity<?> addAll(List<ForecastDto> dtoList) {
        try {
            List<ForecastGas> gasList=new ArrayList<>();
            for (ForecastDto dto : dtoList) {
                ApiResponse apiResponse = add(dto);
                if (!apiResponse.isSuccess()) return converter.apiError409(apiResponse.getMessage());
                else {
                    ApiResponseObject apiResponseObject= (ApiResponseObject) apiResponse;
                    gasList.add((ForecastGas) apiResponseObject.getObject());
                }
            }
            for (int i = 0; i <gasList.size() ; i++) {
                gasList.set(i,forecastGasRepository.save(gasList.get(i)));
            }
            return converter.apiSuccess200(gasList);

        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }















    @Override
    public HttpEntity<?> add(Integer id, int year, Month month, double amount){
        try {
            if (id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            ForecastGas forecast= ForecastGas
                    .builder()
                    .miningSystem(miningSystem.get())
                    .month(month)
                    .year(year)
                    .amount(amount)
                    .build();
            ForecastGas save = forecastGasRepository.save(forecast);
            return converter.apiSuccess201("Forecast created",save);
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    @Override
    public HttpEntity<?> all() {
        try {
            return converter.apiSuccess200(forecastGasRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }


    @Override
    public HttpEntity<?> allByObjectId(Integer id){
        try {
            if (id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            return converter.apiSuccess200(forecastGasRepository.findAllByMiningSystemOrderByCreatedAtAsc(miningSystem.get()));
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    @Override
    public HttpEntity<?> allByObjectAndYearBetween(Integer id, int from, int to) {
        try {
            if (id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            return converter.apiSuccess200(forecastGasRepository.findAllByMiningSystemAndYearBetween(miningSystem.get(),from,to));
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    @Override
    public HttpEntity<?> allByObjectAndYear(Integer id, int year){
        try {
            if (id==null) return converter.apiError400("id is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            return converter.apiSuccess200(forecastGasRepository.findAllByMiningSystemAndYearBetween(miningSystem.get(),year,year));
        }catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409();
        }
    }



}
