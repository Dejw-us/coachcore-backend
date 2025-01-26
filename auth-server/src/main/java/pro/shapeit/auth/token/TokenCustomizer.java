package pro.shapeit.auth.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;
import pro.shapeit.auth.user.AppUser;

import java.security.Principal;
import java.time.Instant;
import java.util.Objects;

import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.ACCESS_TOKEN;

@Component
public class TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
  @Override
  public void customize(JwtEncodingContext context) {
    if (context.getTokenType().equals(ACCESS_TOKEN)) {
      var claims = context.getClaims();
      var user = getAppUserFromContext(context);
      var userId = user.getLocalId();

      claims.expiresAt(Instant.now().plusSeconds(3600L * 24L));
      claims.claim("id", userId);
    }
  }

  private AppUser getAppUserFromContext(JwtEncodingContext context) {
    var auth = Objects.requireNonNull(context.get(OAuth2Authorization.class));
    var token = (UsernamePasswordAuthenticationToken) auth.getAttribute(Principal.class.getName());
    return (AppUser) Objects.requireNonNull(token).getPrincipal();
  }
}
