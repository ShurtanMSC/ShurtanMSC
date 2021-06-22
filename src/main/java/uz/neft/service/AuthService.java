package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.neft.entity.User;
import uz.neft.payload.ApiResponse;
import uz.neft.payload.ApiResponseObject;
import uz.neft.payload.ResToken;
import uz.neft.payload.SignIn;
import uz.neft.repository.UserRepository;
import uz.neft.secret.JwtTokenProvider;


@Service
public class AuthService implements UserDetailsService {

    UserRepository userRepository;
    JwtTokenProvider jwtTokenProvider;
    AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public User loadByUserId(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("user not found"));

    }

    public ResToken signIn(SignIn signIn) {
        try {
//            System.out.println("sign in " + signIn.getPassword());
//            System.out.println("sign in " + signIn.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User principal = (User) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(principal);
            return new ResToken(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ApiResponse searchUser(String search) {
        return new ApiResponseObject("Ok", true, userRepository.byUsername(search));
    }

//    public ApiResponse all() {
//        return new ApiResponse("Ok", true, userRepository.findAll().stream().map(item -> dtoService.userDto(item)).collect(Collectors.toList()));
//    }
}
