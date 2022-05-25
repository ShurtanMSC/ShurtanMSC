package uz.neft.repository.action;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.Uppg;
import uz.neft.entity.action.UppgAction;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UppgActionRepository extends JpaRepository<UppgAction,Long> {

    UppgAction findFirstByUppg(Uppg uppg);

    Optional<UppgAction> findFirstByUppgOrderByCreatedAtDesc(Uppg uppg);

//    Optional<UppgAction> findFirstByUppg(Uppg uppg);

    List<UppgAction> findAllByCreatedAtBetween(Timestamp from, Timestamp until);

    List<UppgAction> findAllByUppgOrderByCreatedAtDesc(Uppg uppg);

    List<UppgAction> findAllByUppgAndCreatedAtBetween(Uppg uppg, Timestamp from, Timestamp until);

}
