package uz.neft.repository.constants;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.variables.CollectionPointConstant;
import uz.neft.entity.variables.Constant;

import java.util.List;

public interface CollectionPointConstantRepository extends JpaRepository<CollectionPointConstant,Integer> {

    CollectionPointConstant findByCollectionPointAndConstant(CollectionPoint collectionPoint, Constant constant);

    List<CollectionPointConstant> findAllByCollectionPoint(CollectionPoint collectionPoint);
}
