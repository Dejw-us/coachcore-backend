package pro.shapeit.auth.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;
import pro.shapeit.auth.user.AppUser;

import java.security.Principal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
public class TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
  @Override
  public void customize(JwtEncodingContext context) {
    var uuid = UUID.randomUUID();
    var claims = context.getClaims();
    var user = getAppUserFromContext(context);
    if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
      claims.expiresAt(Instant.now().plusSeconds(30L));
      claims.claim("id", user.getLocalId());
      log.info("id:{}, Added id claim", uuid);
    }
    log.info("id:{}, token type: {}", uuid, context.getTokenType().getValue());
    log.info("id:{}, grant: {}", uuid, context.getAuthorizationGrantType().getValue());
  }

  private AppUser getAppUserFromContext(JwtEncodingContext context) {
    var auth = Objects.requireNonNull(context.get(OAuth2Authorization.class));
    var token = (UsernamePasswordAuthenticationToken) auth.getAttribute(Principal.class.getName());
    return (AppUser) Objects.requireNonNull(token).getPrincipal();
  }
}
