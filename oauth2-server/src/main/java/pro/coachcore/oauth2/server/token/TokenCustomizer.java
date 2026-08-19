package pro.coachcore.oauth2.server.token;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;
import java.security.Principal;
import java.time.Instant;
import java.util.List;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.oauth2.server.user.User;

@Slf4j
@Component
public class TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
  @Override
  public void customize(JwtEncodingContext context) {
    var claims = context.getClaims();
    var user = getAppUserFromContext(context);

    if (user != null) {
      if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
        claims.expiresAt(Instant.now().plusSeconds(60L));
        if (user.getLocalId() != null) {
          claims.claim("id", user.getLocalId());
        }
        claims.claim("roles",
            user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
      }
      if (context.getTokenType().equals(OAuth2TokenType.REFRESH_TOKEN)) {
        claims.expiresAt(Instant.now().plusSeconds(24 * 3600));
      }
    }

    if (context.getAuthorizationGrantType().equals(CLIENT_CREDENTIALS)) {
      claims.claim("roles", List.of("MAIL_SENDER"));
    }
    log.debug("Claims: {}", claims);
  }

  private @Nullable User getAppUserFromContext(JwtEncodingContext context) {
    var auth = context.get(OAuth2Authorization.class);
    if (auth == null) {
      return null;
    }
    var token = (UsernamePasswordAuthenticationToken) auth.getAttribute(Principal.class.getName());
    if (token != null) {
      return (User) token.getPrincipal();
    }
    return null;
  }
}
