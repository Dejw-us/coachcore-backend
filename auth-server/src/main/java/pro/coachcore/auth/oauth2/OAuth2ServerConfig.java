package pro.coachcore.auth.oauth2;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import pro.coachcore.auth.security.RsaKeyProperties;
import pro.coachcore.auth.token.TokenCookieFilter;
import pro.coachcore.util.HttpUtils;

import java.util.UUID;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class OAuth2ServerConfig {
  private final RsaKeyProperties rsaKeyProperties;
  private final OAuth2ClientsProperties clientsProperties;

  @Value("${issuer}")
  private String issuer;

  @Bean
  RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
    var reactClient = RegisteredClient.withId(clientsProperties.webAppClientId())
        .clientId(clientsProperties.webAppClientId())
        .clientSecret(passwordEncoder.encode(clientsProperties.webAppClientSecret()))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .redirectUri("http://localhost:3000")
        .scope("openid")
        .build();
    var mailClient = RegisteredClient.withId(clientsProperties.mailClientId())
        .clientId(clientsProperties.mailClientId())
        .clientSecret(passwordEncoder.encode(clientsProperties.mailClientSecret()))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .scope("mail.send")
        .build();
    return new InMemoryRegisteredClientRepository(reactClient, mailClient);
  }

  @Bean
  @Order(1)
  SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    var authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer()
        .oidc(withDefaults());
    
    http.csrf(csrf -> csrf.disable());
    http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher());
    http.with(authorizationServerConfigurer, withDefaults());
    http.authorizeHttpRequests(HttpUtils::anyAuthenticated);
    http.exceptionHandling(exceptions -> exceptions
        .defaultAuthenticationEntryPointFor(
            new LoginUrlAuthenticationEntryPoint("http://localhost:8080/account/login"),
            new MediaTypeRequestMatcher(MediaType.TEXT_HTML)));
    http.addFilterBefore(new TokenCookieFilter(), SecurityContextHolderAwareRequestFilter.class);

    return http.build();
  }

  @Bean
  @Order(2)
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/account/register", "/account/login").permitAll();
      auth.requestMatchers(HttpMethod.GET, "/v1/users/public/**").permitAll();
      auth.requestMatchers(HttpMethod.GET, "/refresh-token").permitAll();
      auth.anyRequest().authenticated();
    });
    http.formLogin(form -> form.disable());
    http.addFilterBefore(new TokenCookieFilter(), SecurityContextHolderFilter.class);

    return http.build();
  }

  @Bean
  JWKSource<SecurityContext> jwkSource() {
    var rsaKey = new RSAKey.Builder(rsaKeyProperties.publicKey())
        .privateKey(rsaKeyProperties.privateKey())
        .keyID(UUID.randomUUID().toString())
        .build();
    var jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
  }

  @Bean
  JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  @Bean
  AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder()
        .issuer(issuer)
        .build();
  }
}
