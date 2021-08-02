package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.MiningSystem;

public interface MiningSystemRepository extends JpaRepository<MiningSystem,Integer> {
    boolean existsById(Integer id);
}
