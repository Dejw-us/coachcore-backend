package pro.coachcore.training;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import pro.coachcore.jwt.JwtAuthConverter;

@TestConfiguration
public class TestConfig {
  @Bean
  Converter<Jwt, JwtAuthenticationToken> jwtAuthConverter() {
    return new JwtAuthConverter();
  }
}
