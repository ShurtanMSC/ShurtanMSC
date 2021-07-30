package uz.neft.repository.action;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.Well;
import uz.neft.entity.action.WellAction;

import java.util.List;
import java.util.Optional;

public interface WellActionRepository extends JpaRepository<WellAction,Long> {

    Optional<WellAction> findFirstByWell(Well well);
    List<WellAction> findAllByWell(Well well);

}
