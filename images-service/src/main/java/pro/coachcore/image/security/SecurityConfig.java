package pro.coachcore.image.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain chain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers(HttpMethod.POST, "/v1/profile-pictures/**").authenticated();
      auth.requestMatchers(HttpMethod.GET, "/v1/profile-pictures/**").permitAll();
      auth.anyRequest().permitAll();
    });
    http.oauth2ResourceServer(server -> {
      server.jwt(Customizer.withDefaults());
    });
    return http.build();
  }
}
