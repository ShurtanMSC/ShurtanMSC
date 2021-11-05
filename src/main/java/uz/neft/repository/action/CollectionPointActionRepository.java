package uz.neft.repository.action;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Uppg;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.entity.action.WellAction;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface CollectionPointActionRepository extends JpaRepository<CollectionPointAction, Long> {


//    CollectionPointAction findLast1ByCollectionPoint(CollectionPoint collectionPoint);
    Optional<CollectionPointAction> findFirstByCollectionPointOrderByCreatedAtDesc(CollectionPoint collectionPoint);
    CollectionPointAction findFirstByCollectionPoint(CollectionPoint collectionPoint);
//    CollectionPointAction findFirst1ByOrderByCreatedAtDescAndByCollectionPoint(CollectionPoint collectionPoint);


    List<CollectionPointAction> findAllByCreatedAtBetween(Timestamp from,Timestamp until);

    List<CollectionPointAction> findAllByCollectionPointAndCreatedAtBetween(CollectionPoint collectionPoint,Timestamp from,Timestamp until);

//    List<CollectionPointAction> findAllByCollectionPointOrderByCreatedAtDesc(CollectionPoint collectionPoint);

    Page<CollectionPointAction> findAllByCollectionPointOrderByCreatedAtDesc(CollectionPoint collectionPoint, Pageable pageable);



}
