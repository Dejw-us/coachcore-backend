package pro.shapeit.auth.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;
import pro.shapeit.auth.user.AppUser;

import java.security.Principal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.ACCESS_TOKEN;

@Component
public class TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
  @Override
  public void customize(JwtEncodingContext context) {
    if (context.getTokenType().equals(ACCESS_TOKEN)) {
      var claims = context.getClaims();
      if (context.getAuthorizationGrantType().equals(AuthorizationGrantType.AUTHORIZATION_CODE)) {
        var user = getAppUserFromContext(context);

        claims.expiresAt(Instant.now().plusSeconds(3600L * 24L));
        claims.claim("id", user.getLocalId());
        claims.claim("pid", user.getPublicId());
      }
      if (context.getAuthorizationGrantType().equals(AuthorizationGrantType.CLIENT_CREDENTIALS)) {
        claims.claim("id", UUID.randomUUID());
        claims.claim("pid", UUID.randomUUID());
      }
    }
  }

  private AppUser getAppUserFromContext(JwtEncodingContext context) {
    var auth = Objects.requireNonNull(context.get(OAuth2Authorization.class));
    var token = (UsernamePasswordAuthenticationToken) auth.getAttribute(Principal.class.getName());
    return (AppUser) Objects.requireNonNull(token).getPrincipal();
  }
}
