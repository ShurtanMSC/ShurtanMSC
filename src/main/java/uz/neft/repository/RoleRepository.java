package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Short> {
}
