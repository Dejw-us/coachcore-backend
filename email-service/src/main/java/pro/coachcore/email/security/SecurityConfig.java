package pro.coachcore.email.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import pro.coachcore.jwt.JwtAuthConverter;

@Configuration
public class SecurityConfig {
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.oauth2ResourceServer(oauth2ResourceServer -> {
      oauth2ResourceServer.jwt(jwt -> {
        jwt.jwtAuthenticationConverter(new JwtAuthConverter());
      });
    });
    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/email/**").hasAnyAuthority("ADMIN", "MAIL_SENDER");
      auth.anyRequest().denyAll();
    });
    return http.build();
  }
}
