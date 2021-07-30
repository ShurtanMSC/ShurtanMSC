package uz.neft.repository.constants;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.Uppg;
import uz.neft.entity.variables.Constant;
import uz.neft.entity.variables.ConstantValue;
import uz.neft.entity.variables.UppgConstant;

public interface ConstantValuesRepository extends JpaRepository<ConstantValue,Integer> {

    UppgConstant findByUppgAndConstant(Uppg uppg, Constant constant);

}
