package pro.shapeit.training.security;

import lombok.RequiredArgsConstructor;
import pro.coachcore.common.security.cors.CorsSources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final TrainingPlanAuthorizationManager trainingPlanAuthorizationManager;

  public CorsConfigurationSource corsConfigurationSource() {
    return CorsSources.enableReactClientCorsConfigurationSource();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);

    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers(HttpMethod.OPTIONS, "/v1/**").permitAll();
      auth.requestMatchers(HttpMethod.POST, "/v1/training-plans").authenticated();

      auth.requestMatchers("/home").permitAll();
      auth.requestMatchers("/v1/training-plans/{planId}/**").access(trainingPlanAuthorizationManager);
      auth.requestMatchers("/v1/catalog-exercises").permitAll();

      auth.requestMatchers(HttpMethod.GET, "/v1/exercise-categories").permitAll();
      auth.requestMatchers(HttpMethod.GET, "/v1/training-plans").permitAll();
      auth.requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll();
    });

    http.oauth2ResourceServer(server -> server.jwt(withDefaults()));

    http.sessionManagement(session -> {
      session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    });

    http.addFilterBefore(new JwtLoggingFilter(), BearerTokenAuthenticationFilter.class);

    return http.build();
  }
}
