package pro.coachcore.training.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import pro.coachcore.jwt.JwtAuthConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  Converter<Jwt, JwtAuthenticationToken> jwtConverter() {
    return new JwtAuthConverter();
  }

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
      auth.requestMatchers(HttpMethod.GET, "/v1/rating/{planId}/average").permitAll();
      auth.requestMatchers(HttpMethod.PUT, "/v1/rating/{planId}").authenticated();
      auth.requestMatchers("/v1/saved-plans/**").authenticated();
      auth.requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll();
      auth.requestMatchers("/test/**").permitAll();
    });

    http.oauth2ResourceServer(server -> server.jwt(jwt -> {
      jwt.jwtAuthenticationConverter(jwtConverter());
    }));

    http.sessionManagement(session -> {
      session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    });

    http.addFilterBefore(new JwtLoggingFilter(), BearerTokenAuthenticationFilter.class);

    return http.build();
  }
}
