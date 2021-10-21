package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.neft.dto.special.CollectionPointAndWells;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Uppg;
import uz.neft.entity.Well;

import java.util.List;

public interface WellRepository extends JpaRepository<Well,Integer> {

    List<Well> findAllByCollectionPoint(CollectionPoint collectionPoint);
    List<Well> findAllByCollectionPointOrderByIdAsc(CollectionPoint collectionPoint);

    @Query(value="select * from well where collection_point_id in (select id from collection_point where uppg_id = :uppg_id) order by well.id asc", nativeQuery = true)
    List<Well> findAllByUppgId(Integer uppg_id);

    boolean existsByCollectionPointAndId(CollectionPoint collectionPoint, Integer id);

    @Query(value="select * from well where collection_point_id in (select id from collection_point where uppg_id in (select id from uppg where mining_system_id=:mining_system_id)) order by well.id asc", nativeQuery = true)
    List<Well> findAllByMiningSystemId(Integer mining_system_id);

    @Query(value="select * from well where collection_point_id in (select id from collection_point where uppg_id in (select id from uppg where mining_system_id=:mining_system_id)) order by well.number asc", nativeQuery = true)
    List<Well> findAllByMiningSystemIdSorted(Integer mining_system_id);



}
