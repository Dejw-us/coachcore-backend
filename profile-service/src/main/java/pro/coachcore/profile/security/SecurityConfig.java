package pro.coachcore.profile.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.oauth2ResourceServer(server -> server.jwt(Customizer.withDefaults()));
    http.csrf(AbstractHttpConfigurer::disable);
    http.authorizeHttpRequests(auth -> {
      // auth.requestMatchers(HttpMethod.POST, "/v1/avatars").authenticated();
      // auth.requestMatchers(HttpMethod.GET, "/v1/avatars/*").permitAll();
      auth.anyRequest().permitAll();
    });
    return http.build();
  }
}
