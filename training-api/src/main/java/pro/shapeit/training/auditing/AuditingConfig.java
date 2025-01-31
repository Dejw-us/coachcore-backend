package pro.shapeit.training.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import pro.shapeit.jpa.auditing.JwtAuthenticationTokenAuditorAware;

@Configuration
public class AuditingConfig {
  @Bean
  public AuditorAware<String> auditorAware() {
    return new JwtAuthenticationTokenAuditorAware();
  }
}
