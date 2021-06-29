package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.neft.dto.UserDto;
import uz.neft.entity.Role;
import uz.neft.entity.User;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.RoleRepository;
import uz.neft.repository.UserRepository;
import uz.neft.utils.Converter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Converter converter;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, Converter converter, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.converter = converter;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApiResponse save(UserDto dto) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError();
        }
    }

    public ApiResponse edit(UserDto dto) {
        try {
            User editingUser = new User();
            Optional<User> byId = userRepository.findById(dto.getId());
            if (byId.isPresent()) {
                editingUser = byId.get();
            }
            editingUser.setActive(dto.isActive());
            editingUser.setEmail(dto.getEmail());
            editingUser.setUsername(dto.getUsername());
            editingUser.setFio(dto.getFio());

            if (!passwordEncoder.matches(editingUser.getPassword(), dto.getPassword())) {
                editingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            }

            editingUser.setPhone(dto.getPhone());
            Optional<Role> byId1 = roleRepository.findById(dto.getRoleId());
            editingUser.setRoles(Collections.singleton(byId1.get()));
            User editedUser = userRepository.save(editingUser);
            return converter.apiSuccess("Edited", editedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError();
        }
    }

    public ApiResponse findById(Integer id) {
        try {
            if (id != null) {
                Optional<User> byId = userRepository.findById(id);
                if (byId.isPresent()) {
                    Optional<User> byId1 = userRepository.findById(id);
                    return converter.apiSuccess(byId1.get());
                } else {
                    return converter.apiError("User not found");
                }
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in finding user", e);
        }
    }

    public ApiResponse findAll() {
        try {
            List<User> all = userRepository.findAll();
            List<UserDto> collect = all.stream().map(item -> converter.userToUserDto(item)).collect(Collectors.toList());

            return converter.apiSuccess(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in finding user", e);
        }
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
