package pro.coachcore.training.jwt;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import pro.coachcore.common.test.security.jwt.JwtTestContext;

@TestConfiguration
public class JwtTestConfig {
  @Bean
  JwtTestContext jwtTestContext() {
    return JwtTestContext.create(2);
  }
}
