package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;

import java.util.List;

public interface UppgRepository extends JpaRepository<Uppg,Integer> {

    List<Uppg> findAllByMiningSystem(MiningSystem miningSystem);
}
