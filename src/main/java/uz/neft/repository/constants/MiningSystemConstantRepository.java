package uz.neft.repository.constants;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.variables.Constant;
import uz.neft.entity.variables.MiningSystemConstant;

import java.util.List;

public interface MiningSystemConstantRepository extends JpaRepository<MiningSystemConstant,Integer> {

    MiningSystemConstant findByMiningSystemAndConstant(MiningSystem miningSystem, Constant constant);


    List<MiningSystemConstant>  findAllByMiningSystem(MiningSystem miningSystem);

}
