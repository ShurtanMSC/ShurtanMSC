package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.MiningSystem;

import java.util.List;

public interface MiningSystemRepository extends JpaRepository<MiningSystem,Integer> {
    boolean existsById(Integer id);
    List<MiningSystem> findAllByOrderById();
    List<MiningSystem> findAllByOrderByIdDesc();
}
