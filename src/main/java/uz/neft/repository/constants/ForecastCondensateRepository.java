package uz.neft.repository.constants;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.ForecastCondensate;
import uz.neft.entity.ForecastCondensate;
import uz.neft.entity.MiningSystem;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface ForecastCondensateRepository extends JpaRepository<ForecastCondensate,Integer> {
    List<ForecastCondensate> findAllByMiningSystem(MiningSystem miningSystem);
    List<ForecastCondensate> findAllByYear(int year);
    List<ForecastCondensate> findAllByMonth(Month month);
    List<ForecastCondensate> findAllByYearAndMonth(int year, Month month);
    List<ForecastCondensate> findAllByYearAndMiningSystem(int year, MiningSystem miningSystem);

    List<ForecastCondensate> findAllByYearBetween(int from, int to);

    List<ForecastCondensate> findAllByMiningSystemAndYearBetween(MiningSystem miningSystem, int from, int to);
    List<ForecastCondensate> findAllByMiningSystemAndYearBetweenOrderByCreatedAtAsc(MiningSystem miningSystem, int from, int to);
    Optional<ForecastCondensate> findByMiningSystemAndYearAndMonth(MiningSystem miningSystem, int year, Month month);
}
