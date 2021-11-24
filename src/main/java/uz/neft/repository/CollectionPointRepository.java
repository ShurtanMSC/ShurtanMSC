package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;

import java.util.List;

public interface CollectionPointRepository extends JpaRepository<CollectionPoint,Integer> {
    List<CollectionPoint> findAllByUppg(Uppg uppg);
    List<CollectionPoint> findAllByUppgOrderByIdAsc(Uppg uppg);


    @Query(value="select * from collection_point where uppg_id in (select id from uppg where mining_system_id = :mining_system_id)", nativeQuery = true)
    List<CollectionPoint> findAllByMiningSystemId(Integer mining_system_id);

    @Query(value="select * from collection_point where uppg_id in (select id from uppg where mining_system_id = :mining_system_id) AND collection_point.activee=true", nativeQuery = true)
    List<CollectionPoint> findAllByMiningSystemIdAndActiveTrue(Integer mining_system_id);

    @Query(value="select * from collection_point where uppg_id in (select id from uppg where mining_system_id = :mining_system_id) order by collection_point.id asc ", nativeQuery = true)
    List<CollectionPoint> findAllByMiningSystemIdOrder(Integer mining_system_id);

}
