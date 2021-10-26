package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.OpcServer;

import java.util.List;

public interface OpcServerRepository extends JpaRepository<OpcServer,Integer> {

    List<OpcServer> findAllByOrderByCreatedAtDesc();

}
