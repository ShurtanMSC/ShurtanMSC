package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.variables.MiningSystemGasComposition;

import java.util.List;

public interface MiningSystemGasCompositionRepository extends JpaRepository<MiningSystemGasComposition, Integer> {

    List<MiningSystemGasComposition> findAllByMiningSystem(Integer id);

}
