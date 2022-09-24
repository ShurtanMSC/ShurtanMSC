package uz.neft.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.neft.entity.User;

import java.util.Optional;
import java.util.UUID;

public class AuditorAwareImpl implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication == null || authentication.isAuthenticated()
                || "anonymousUser".equals("" + authentication.getPrincipal()))) {
            try {
                return Optional.of(((User) authentication.getPrincipal()).getId());
            }catch (Exception e){
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}

