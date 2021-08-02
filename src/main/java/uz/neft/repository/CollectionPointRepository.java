package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Uppg;

import java.util.List;

public interface CollectionPointRepository extends JpaRepository<CollectionPoint,Integer> {

    List<CollectionPoint> findAllByUppg(Uppg uppg);

}
