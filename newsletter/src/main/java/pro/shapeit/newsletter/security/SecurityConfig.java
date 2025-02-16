package pro.shapeit.newsletter.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Value("${ADMIN_USERNAME:admin}")
  private String adminUsername;

  @Value("${ADMIN_PASSWORD:admin}")
  private String adminPassword;

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> {
      csrf.ignoringRequestMatchers("/newsletter/subscribe");
    });
    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/admin/**").hasAuthority("ADMIN");
      auth.anyRequest().permitAll();
    });
    http.formLogin(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    var admin = User.withUsername(adminUsername)
        .password(passwordEncoder.encode(adminPassword))
        .authorities("ADMIN")
        .build();
    return new InMemoryUserDetailsManager(admin);
  }
}
