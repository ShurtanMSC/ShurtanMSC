package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Uppg;
import uz.neft.entity.Well;

import java.util.List;

public interface WellRepository extends JpaRepository<Well,Integer> {

    List<Well> findAllByCollectionPoint(CollectionPoint collectionPoint);

}
