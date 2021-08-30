package uz.neft.service;

import org.springframework.http.HttpEntity;
import uz.neft.dto.ForecastDto;
import uz.neft.payload.ApiResponse;

import java.time.Month;
import java.util.List;

public interface ForecastMethodInterface {

    ApiResponse add(ForecastDto dto);
    HttpEntity<?> addDto(ForecastDto dto);
    HttpEntity<?> addAll(List<ForecastDto> dtoList);
    HttpEntity<?> add(Integer id, int year, Month month, double amount);
    HttpEntity<?> all();
    HttpEntity<?> allByObjectId(Integer id);
    HttpEntity<?> allByObjectAndYearBetween(Integer id, int from, int to);
    HttpEntity<?> allByObjectAndYear(Integer id, int year);

}
