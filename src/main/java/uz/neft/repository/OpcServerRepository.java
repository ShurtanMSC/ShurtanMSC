package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.OpcServer;

public interface OpcServerRepository extends JpaRepository<OpcServer,Integer> {
}
