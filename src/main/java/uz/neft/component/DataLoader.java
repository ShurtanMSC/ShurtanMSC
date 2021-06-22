package uz.neft.component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.neft.entity.Role;
import uz.neft.entity.RoleName;
import uz.neft.entity.User;
import uz.neft.repository.RoleRepository;
import uz.neft.repository.UserRepository;


import java.util.Collections;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Value("${spring.datasource.initialization-mode}")
//    private String mode;

    @Override
    public void run(String... args) throws Exception {
//        if (mode.equals("always")) {
            Role director = roleRepository.save(new Role(RoleName.SUPER_ADMIN));
            userRepository.save(
                    User
                            .builder()
                            .active(true)
                            .email("a")
                            .password(passwordEncoder.encode("a"))
                            .fio("a")
                            .phone("a")
                            .roles(Collections.singleton(director))
                            .username("a")
                            .build()
            );
//        }
    }
}
