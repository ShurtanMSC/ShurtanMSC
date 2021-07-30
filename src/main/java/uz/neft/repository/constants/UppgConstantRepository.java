package uz.neft.repository.constants;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.Uppg;
import uz.neft.entity.variables.Constant;
import uz.neft.entity.variables.UppgConstant;

public interface UppgConstantRepository extends JpaRepository<UppgConstant,Integer> {

    UppgConstant findByUppgAndConstant(Uppg uppg, Constant constant);

}
