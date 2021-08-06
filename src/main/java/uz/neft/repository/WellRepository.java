package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Uppg;
import uz.neft.entity.Well;

import java.util.List;

public interface WellRepository extends JpaRepository<Well,Integer> {

    List<Well> findAllByCollectionPoint(CollectionPoint collectionPoint);

    @Query(value="select * from well where collection_point_id in (select id from collection_point where uppg_id = :uppg_id)", nativeQuery = true)
    List<Well> findAllByUppgId(Integer uppg_id);


}
