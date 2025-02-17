package pro.coachcore.auth.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration(proxyBeanMethods = false)
public class AuthClientConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
    http.oauth2Login(withDefaults());

    return http.build();
  }

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    var client = ClientRegistration.withRegistrationId("shapeit")
        .clientId("shapeit")
        .clientSecret("secret")
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUri("http://localhost:3000")
        .authorizationUri("http://localhost:9000/oauth2/authorize")
        .tokenUri("http://localhost:9000/oauth2/token")
        .scope("openid")
        .issuerUri("http://localhost:9000")
        .build();
    return new InMemoryClientRegistrationRepository(client);
  }
}
