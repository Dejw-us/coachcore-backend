package pro.shapeit.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);

    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/training-plans/**").permitAll();
      auth.anyRequest().authenticated();
    });

    http.oauth2ResourceServer(server -> {
      server.jwt(jwt -> {
        jwt.jwtAuthenticationConverter(jwtAuthenticationConverter());
      });
    });

    http.sessionManagement(session -> {
      session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    });

    return http.build();
  }

  @SuppressWarnings("unchecked")
  private JwtAuthenticationConverter jwtAuthenticationConverter() {
    var converter = new JwtAuthenticationConverter();

    converter.setJwtGrantedAuthoritiesConverter(jwt -> {
      var shapeit = (Map<String, Object>) jwt.getClaimAsMap("resource_access")
          .getOrDefault("shapeit-api", Map.of());
      var roles = (List<String>) shapeit.getOrDefault("roles", List.of());

      return roles.stream()
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toSet());
    });

    return converter;
  }
}
