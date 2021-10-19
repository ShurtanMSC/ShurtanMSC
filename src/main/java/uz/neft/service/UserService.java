package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import java.util.HashSet;
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

    public ResponseEntity<?> save(UserDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            if (!roleRepository.existsById(dto.getRoleId())) return converter.apiError404("Role id does not exist");
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
            UserDto userDto = converter.userToUserDto(user);
            return converter.apiSuccess201("User saved", userDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error catch");
        }
    }

    public ResponseEntity<?> edit(UserDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError400("id is null");

            User editingUser;
            Optional<User> byId = userRepository.findById(dto.getId());
            if (byId.isPresent()) {
                editingUser = byId.get();
                editingUser.setActive(dto.isActive());
                editingUser.setEmail(dto.getEmail());
                editingUser.setUsername(dto.getUsername());
                editingUser.setFio(dto.getFio());
                editingUser.setPhone(dto.getPhone());
                Optional<Role> role = roleRepository.findById(dto.getRoleId());
                if (role.isPresent()) {
                    editingUser.setRoles(new HashSet<>(Collections.singletonList(role.get())));
                }
                if (dto.getPassword() != null && dto.getPassword().length()>3 && !passwordEncoder.matches(byId.get().getPassword(), dto.getPassword())) {
                    editingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
                }
                editingUser = userRepository.save(editingUser);
                UserDto userDto = converter.userToUserDto(editingUser);
                return converter.apiSuccess200("User edited", userDto);
            }
            return converter.apiError404("User not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error catch");
        }
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            if (id != null) {
                Optional<User> byId = userRepository.findById(id);
                if (byId.isPresent()) {
                    userRepository.deleteById(id);
                    return converter.apiSuccess200("Deleted");
                } else {
                    return converter.apiError404("User not found");
                }
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in deleting", e);
        }
    }

    public ResponseEntity<?> findById(Integer id) {
        try {
            if (id != null) {
                Optional<User> byId = userRepository.findById(id);
                if (byId.isPresent()) {
                    UserDto userDto = converter.userToUserDto(byId.get());
                    return converter.apiSuccess200(userDto);
                } else {
                    return converter.apiError404("User not found");
                }
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in finding user", e);
        }
    }

    public ResponseEntity<?> findAll() {
        try {
            List<User> all = userRepository.findAll();
            List<UserDto> collect = all.stream().map(converter::userToUserDto).collect(Collectors.toList());

            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in finding user", e);
        }
    }

}
