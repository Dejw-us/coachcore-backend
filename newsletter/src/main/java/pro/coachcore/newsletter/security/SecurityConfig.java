package pro.coachcore.newsletter.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import pro.coachcore.jwt.JwtAuthConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> {
      csrf.ignoringRequestMatchers("/newsletter/subscribe");
    });
    http.oauth2ResourceServer(server -> server
        .jwt(jwt -> jwt
            .jwtAuthenticationConverter(new JwtAuthConverter())));
    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/admin/**").hasAuthority("ADMIN");
      auth.anyRequest().permitAll();
    });
    http.formLogin(Customizer.withDefaults());
    return http.build();
  }
}
