package pro.coachcore.training.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import pro.coachcore.jpa.auditing.JwtAuthenticationTokenAuditorAware;

@Configuration
public class JpaConfig {
  @Bean
  AuditorAware<String> auditorAware() {
    return new JwtAuthenticationTokenAuditorAware();
  }
}
