package uz.neft.repository.constants;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.Well;
import uz.neft.entity.variables.Constant;
import uz.neft.entity.variables.WellConstant;

import java.util.List;

public interface WellConstantRepository extends JpaRepository<WellConstant,Integer> {

    WellConstant findByWellAndConstant(Well well, Constant constant);

    List<WellConstant> findAllByWell(Well well);
}
