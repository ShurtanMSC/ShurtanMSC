package uz.neft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.neft.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
}
