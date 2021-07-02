package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.MiningSystemConstant;
import uz.neft.entity.MiningSystemGasComposition;

public interface MiningSystemGasCompositionRepository extends JpaRepository<MiningSystemGasComposition, Integer> {


}
