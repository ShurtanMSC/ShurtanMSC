package uz.neft.repository.action;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Well;
import uz.neft.entity.action.MiningSystemAction;
import uz.neft.entity.action.WellAction;

import java.util.List;
import java.util.Optional;

public interface MiningSystemActionRepository extends JpaRepository<MiningSystemAction,Long> {

    Optional<MiningSystemAction> findFirstByMiningSystem(MiningSystem miningSystem);
    List<MiningSystemAction> findAllByMiningSystem(MiningSystem miningSystem);

}
