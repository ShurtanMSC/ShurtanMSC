package uz.neft.repository.constants;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.variables.Constant;
import uz.neft.entity.variables.ConstantNameEnums;

public interface ConstantRepository extends JpaRepository<Constant,Integer> {

    Constant findByName(ConstantNameEnums constantNameEnums);

}
