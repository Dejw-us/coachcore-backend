package pro.coachcore.training.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;
import pro.coachcore.jwt.JwtAuthConverter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http,
      TrainingPlanAuthorizationManager trainingPlanAuthorizationManager) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);

    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers(HttpMethod.OPTIONS, "/v1/**").permitAll();
      auth.requestMatchers(HttpMethod.POST, "/v1/training-plans").authenticated();

      auth.requestMatchers("/v1/training-plans/me").authenticated();
      auth.requestMatchers("/v1/training-plans/{planId}/**")
          .access(trainingPlanAuthorizationManager);
      auth.requestMatchers(HttpMethod.POST, "/v1/catalog-exercises").hasAuthority("ADMIN");
      auth.requestMatchers(HttpMethod.GET, "/v1/catalog-exercises").permitAll();

      auth.requestMatchers(HttpMethod.GET, "/v1/exercise-categories").permitAll();
      auth.requestMatchers(HttpMethod.GET, "/v1/training-plans").permitAll();
      auth.requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll();
    });

    http.oauth2ResourceServer(server -> server.jwt(jwt -> {
      jwt.jwtAuthenticationConverter(new JwtAuthConverter());
    }));

    http.sessionManagement(session -> {
      session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    });

    http.addFilterBefore(new JwtLoggingFilter(), BearerTokenAuthenticationFilter.class);

    return http.build();
  }
}
