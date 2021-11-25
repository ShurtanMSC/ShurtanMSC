package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.NumberOfStaff;
import uz.neft.entity.action.CollectionPointAction;

import java.util.Optional;

public interface NumberOfStaffRepository extends JpaRepository<NumberOfStaff,Integer> {
    Optional<NumberOfStaff> findFirstByMiningSystemOrderByCreatedAtDesc(MiningSystem miningSystem);
}
