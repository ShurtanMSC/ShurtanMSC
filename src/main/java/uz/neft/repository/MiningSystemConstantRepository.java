package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.variables.Constant;
import uz.neft.entity.variables.MiningSystemConstant;

public interface MiningSystemConstantRepository extends JpaRepository<MiningSystemConstant,Integer> {

    Double findByMiningSystemAndConstant(MiningSystem miningSystem, Constant constant);

}