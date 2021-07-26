package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.variables.GasComposition;

public interface GasCompositionRepository extends JpaRepository<GasComposition,Integer> {


}
