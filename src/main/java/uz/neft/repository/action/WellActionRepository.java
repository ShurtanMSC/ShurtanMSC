package uz.neft.repository.action;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Uppg;
import uz.neft.entity.Well;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.entity.action.UppgAction;
import uz.neft.entity.action.WellAction;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface WellActionRepository extends JpaRepository<WellAction,Long> {

    Optional<WellAction> findFirstByWell(Well well);
    Optional<WellAction> findFirstByWellOrderByCreatedAtDesc(Well well);
//    Optional<WellAction> findFirstByWellOrderByCreatedAtDesc(Well well);

//    Optional<WellAction> findFirstByWell(Well well);

    Page<WellAction> findAllByWellOrderByCreatedAtDesc(Well well, Pageable pageable);


    List<WellAction> findAllByWell(Well well);

    List<WellAction> findAllByCreatedAtBetween(Timestamp from, Timestamp until);

    List<WellAction> findAllByWellAndCreatedAtBetween(Well well, Timestamp from, Timestamp until);


}
