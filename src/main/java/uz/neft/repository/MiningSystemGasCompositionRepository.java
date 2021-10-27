package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.variables.GasComposition;
import uz.neft.entity.variables.MiningSystemGasComposition;

import java.util.List;
import java.util.Optional;

public interface MiningSystemGasCompositionRepository extends JpaRepository<MiningSystemGasComposition, Integer> {

    List<MiningSystemGasComposition> findAllByMiningSystemOrderByGasCompositionId(MiningSystem miningSystem);
    List<MiningSystemGasComposition> findAllByMiningSystem(MiningSystem miningSystem);

    Optional<MiningSystemGasComposition> findByIdAndMiningSystemAndGasComposition(Integer id, MiningSystem miningSystem, GasComposition composition);

    Optional<MiningSystemGasComposition> findFirstByMiningSystemAndGasComposition(MiningSystem miningSystem, GasComposition composition);
    Optional<MiningSystemGasComposition> findFirstByMiningSystemAndGasCompositionOrderByCreatedAt(MiningSystem miningSystem, GasComposition composition);


}
