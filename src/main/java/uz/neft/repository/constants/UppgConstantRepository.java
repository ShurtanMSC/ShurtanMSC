package uz.neft.repository.constants;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.variables.Constant;
import uz.neft.entity.variables.MiningSystemConstant;
import uz.neft.entity.variables.UppgConstant;

import java.util.List;

public interface UppgConstantRepository extends JpaRepository<UppgConstant,Integer> {

    UppgConstant findByUppgAndConstant(Uppg uppg, Constant constant);

    List<UppgConstant> findAllByUppg(Uppg uppg);
}
