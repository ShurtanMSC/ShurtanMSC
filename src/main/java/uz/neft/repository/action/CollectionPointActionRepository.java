package uz.neft.repository.action;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.entity.action.WellAction;

public interface CollectionPointActionRepository extends JpaRepository<CollectionPointAction,Long> {
}
