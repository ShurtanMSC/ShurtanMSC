package uz.neft.repository.constants;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.ForecastGas;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface ForecastGasRepository extends JpaRepository<ForecastGas,Integer> {
    List<ForecastGas> findAllByMiningSystem(MiningSystem miningSystem);
    List<ForecastGas> findAllByMiningSystemOrderByCreatedAtAsc(MiningSystem miningSystem);
    List<ForecastGas> findAllByYear(int year);
    List<ForecastGas> findAllByMonth(Month month);
    List<ForecastGas> findAllByYearAndMonth(int year, Month month);
    List<ForecastGas> findAllByYearAndMiningSystem(int year, MiningSystem miningSystem);

    List<ForecastGas> findAllByYearBetween(int from, int to);

    List<ForecastGas> findAllByMiningSystemAndYearBetween(MiningSystem miningSystem, int from, int to);
    List<ForecastGas> findAllByMiningSystemAndYearBetweenOrderByCreatedAtAsc(MiningSystem miningSystem, int from, int to);
    Optional<ForecastGas> findByMiningSystemAndYearAndMonth(MiningSystem miningSystem, int year, Month month);
}
