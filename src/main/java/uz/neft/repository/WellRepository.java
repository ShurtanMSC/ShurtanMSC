package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.Uppg;
import uz.neft.entity.Well;

public interface WellRepository extends JpaRepository<Well,Integer> {

}
