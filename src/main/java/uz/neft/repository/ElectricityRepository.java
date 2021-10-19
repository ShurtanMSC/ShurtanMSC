package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.Electricity;
import uz.neft.entity.MiningSystem;

import java.util.List;
import java.util.Optional;

public interface ElectricityRepository extends JpaRepository<Electricity,Integer> {
    Optional<Electricity> findFirstByMiningSystem(MiningSystem miningSystem);
    Optional<Electricity> findFirstByMiningSystemOrderByCreatedAtDesc(MiningSystem miningSystem);

}
