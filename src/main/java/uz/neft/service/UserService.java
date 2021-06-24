package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.neft.dto.UserDto;
import uz.neft.entity.User;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.RoleRepository;
import uz.neft.repository.UserRepository;
import uz.neft.utils.Converter;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Converter converter;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponse save(UserDto dto) {

        if (dto.getId() != null) return converter.apiError();
        if (!roleRepository.existsById(dto.getRoleId())) return converter.apiError();
        User user = User
                .builder()
                .username(dto.getUsername())
                .phone(dto.getPhone())
                .fio(dto.getFio())
                .active(dto.isActive())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Collections.singleton(roleRepository.findById(dto.getRoleId()).get()))
                .build();
        user = userRepository.save(user);
        return converter.apiSuccess("Saved", user);
    }

    public ApiResponse delete(Integer id) {

        try {
            if (id != null) {
                Optional<User> byId = userRepository.findById(id);
                if (byId.isPresent()) {
                    userRepository.deleteById(id);
                    return converter.apiSuccess("Deleted");
                } else {
                    return converter.apiError("User not found");
                }
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in deleting", e);
        }
    }

}
