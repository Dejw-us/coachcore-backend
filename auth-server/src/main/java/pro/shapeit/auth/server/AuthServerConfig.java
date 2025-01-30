package pro.shapeit.auth.server;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import pro.shapeit.auth.security.RsaKeyProperties;
import pro.shapeit.util.HttpUtils;

import java.util.UUID;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthServerConfig {
  private final RsaKeyProperties rsaKeyProperties;

  @Bean
  public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
    var client = RegisteredClient.withId("shapeit")
        .clientId("shapeit")
        .clientSecret(passwordEncoder.encode("secret"))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .redirectUri("https://oidcdebugger.com/debug")
        .scope("openid")
        .build();
    return new InMemoryRegisteredClientRepository(client);
  }

  @Bean
  @Order(1)
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    var authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer()
        .oidc(withDefaults());

    http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher());
    http.with(authorizationServerConfigurer, withDefaults());
    http.authorizeHttpRequests(HttpUtils::anyAuthenticated);
    http.exceptionHandling(exceptions -> exceptions
        .defaultAuthenticationEntryPointFor(
            new LoginUrlAuthenticationEntryPoint("/account/login"),
            new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
        )
    );

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/account/register", "/account/login").permitAll();
      auth.requestMatchers(HttpMethod.GET, "/v1/users/public/**").permitAll();
      auth.anyRequest().authenticated();
    });
    http.formLogin(form -> {
      form.loginPage("/account/login");
    });

    return http.build();
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    var rsaKey = new RSAKey.Builder(rsaKeyProperties.publicKey())
        .privateKey(rsaKeyProperties.privateKey())
        .keyID(UUID.randomUUID().toString())
        .build();
    var jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
  }

  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    var issuer = System.getenv("ISSUER");
    if (issuer == null || issuer.isBlank()) {
      issuer = "http://localhost:9000";
    }
    return AuthorizationServerSettings.builder()
        .issuer(issuer)
        .build();
  }
}
