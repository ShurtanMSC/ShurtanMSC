package uz.neft.service.security;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.neft.entity.RoleName;
import uz.neft.entity.User;
import uz.neft.payload.ApiResponse;
import uz.neft.payload.ApiResponseObject;
import uz.neft.payload.ResToken;
import uz.neft.payload.SignIn;
import uz.neft.repository.UserRepository;
import uz.neft.secret.JwtTokenProvider;

import java.util.Objects;
@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    DaoAuthenticationProvider authenticationManager;
    @Autowired
    Logger logger;


    public ResToken signIn(SignIn signIn) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User principal = (User) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(principal);
            return new ResToken(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return null;
        }
    }

    public ResToken signInAdminPanel(SignIn signIn) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User principal = (User) authentication.getPrincipal();
            for (GrantedAuthority authority : principal.getAuthorities()) {
                if (Objects.equals(authority.getAuthority(), RoleName.SUPER_ADMIN.name())){
                    String jwt = jwtTokenProvider.generateToken(principal);
                    return new ResToken(jwt);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return null;
        }
    }

    public ApiResponse searchUser(String search) {
        return new ApiResponseObject("Ok", true, userRepository.byUsername(search));
    }

}
