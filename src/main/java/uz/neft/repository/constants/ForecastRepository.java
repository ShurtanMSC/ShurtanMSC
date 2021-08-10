package uz.neft.repository.constants;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.MiningSystemForecast;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface ForecastRepository extends JpaRepository<MiningSystemForecast,Integer> {
    List<MiningSystemForecast> findAllByMiningSystem(MiningSystem miningSystem);
    List<MiningSystemForecast> findAllByYear(int year);
    List<MiningSystemForecast> findAllByMonth(Month month);
    List<MiningSystemForecast> findAllByYearAndMonth(int year, Month month);
    List<MiningSystemForecast> findAllByYearAndMiningSystem(int year,MiningSystem miningSystem);

    List<MiningSystemForecast> findAllByYearBetween(int from, int to);

    List<MiningSystemForecast> findAllByMiningSystemAndYearBetween(MiningSystem miningSystem,int from, int to);
    Optional<MiningSystemForecast> findByMiningSystemAndYearAndMonth(MiningSystem miningSystem,int year,Month month);
}
