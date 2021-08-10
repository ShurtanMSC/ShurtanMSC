package uz.neft.service;

import org.springframework.http.HttpEntity;

import java.time.Month;

public interface ForecastMethodInterface {

    HttpEntity<?> addForecast(Integer id, int year, Month month);
    HttpEntity<?> allForecast();
    HttpEntity<?> allForecastByObjectId(Integer id);
    HttpEntity<?> allForecastByObjectAndYearBetween(Integer id, int from, int to);
    HttpEntity<?> allForecastByObjectAndYear(Integer id, int year);

}
