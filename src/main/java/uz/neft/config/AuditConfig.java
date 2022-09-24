package uz.neft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
//    @Autowired
//    private AuditorAwareImpl auditorAware;

    @Bean
    public AuditorAware<Integer> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}

