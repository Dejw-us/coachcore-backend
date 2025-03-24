package pro.coachcore.oauth2.server.oauth2;

import static org.springframework.security.config.Customizer.withDefaults;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.oauth2.server.security.RsaKeyProperties;
import pro.coachcore.util.HttpUtils;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class OAuth2ServerConfig {
  private final RsaKeyProperties rsaKeyProperties;

  @Value("${issuer}")
  private String issuer;

  @Bean
  @Order(1)
  SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    var authorizationServerConfigurer =
        OAuth2AuthorizationServerConfigurer.authorizationServer().oidc(withDefaults());

    http.csrf(csrf -> csrf.disable());
    http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher());
    http.with(authorizationServerConfigurer, withDefaults());
    http.authorizeHttpRequests(HttpUtils::anyAuthenticated);
    http.exceptionHandling(exceptions -> exceptions.defaultAuthenticationEntryPointFor(
        new LoginUrlAuthenticationEntryPoint("/account/login"),
        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)));
    return http.build();
  }

  @Bean
  JWKSource<SecurityContext> jwkSource() {
    var rsaKey = new RSAKey.Builder(rsaKeyProperties.publicKey())
        .privateKey(rsaKeyProperties.privateKey()).keyID(UUID.randomUUID().toString()).build();
    var jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
  }

  @Bean
  JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  @Bean
  AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder().issuer(issuer).build();
  }
}
