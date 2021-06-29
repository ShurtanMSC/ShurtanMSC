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
        try {
            Role director = roleRepository.save(new Role(RoleName.SUPER_ADMIN));
            Role operator = roleRepository.save(new Role(RoleName.OPERATOR));
            Role employee = roleRepository.save(new Role(RoleName.EMPLOYEE));
            Role energetic = roleRepository.save(new Role(RoleName.ENERGETIC));
            Role metrologist = roleRepository.save(new Role(RoleName.METROLOGIST));
            Role geologist = roleRepository.save(new Role(RoleName.GEOLOGIST));
            userRepository.save(
                    User
                            .builder()
                            .active(true)
                            .email("admin")
                            .password(passwordEncoder.encode("admin"))
                            .fio("admin")
                            .phone("+998993793877")
                            .roles(Collections.singleton(director))
                            .username("admin")
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
        }

//        }
    }
}
