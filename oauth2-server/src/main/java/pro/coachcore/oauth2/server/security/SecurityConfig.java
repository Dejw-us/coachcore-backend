package pro.coachcore.oauth2.server.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import pro.coachcore.oauth2.common.CookieTokensNames;

@Configuration
class SecurityConfig {
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Order(2)
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.oauth2ResourceServer(server -> server.jwt(withDefaults()));
    http.csrf(AbstractHttpConfigurer::disable);
    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/account/register", "/account/login").permitAll();
      auth.requestMatchers(HttpMethod.GET, "/v1/users/public/**").permitAll();
      auth.anyRequest().authenticated();
    });
    http.formLogin(form -> form.loginPage("/account/login"));
    http.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("http://localhost:3000")
        .invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID",
            CookieTokensNames.ACCESS_TOKEN, CookieTokensNames.ID_TOKEN,
            CookieTokensNames.REFRESH_TOKEN));

    return http.build();
  }
}
