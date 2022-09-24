package uz.neft.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.neft.entity.User;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<Integer> {
    @Override
    @Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        try {
            Optional<Integer> id = Optional.of(((User) authentication.getPrincipal()).getId());
            System.out.println("OOOOOOOOOOOOOOOO");
            System.out.println(id);
            return id;
        }catch (Exception e){
//            e.printStackTrace();
            if (authentication==null||authentication.isAuthenticated()) return Optional.of(0);
            return Optional.empty();
        }


//        return  Optional.of(((User) authentication.getPrincipal()).getId());
    }
}
