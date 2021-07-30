package uz.neft.repository.action;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.Well;
import uz.neft.entity.action.WellAction;

import java.util.List;

public interface WellActionRepository extends JpaRepository<WellAction,Long> {

    WellAction findByWell(Well well);

}
