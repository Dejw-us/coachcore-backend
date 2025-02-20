package pro.coachcore.auth.token;

import lombok.extern.slf4j.Slf4j;
import pro.coachcore.auth.user.AppUser;
import pro.coachcore.auth.user.UserRole;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.Instant;
import java.util.Objects;

@Component
@Slf4j
public class TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
  @Override
  public void customize(JwtEncodingContext context) {
    var claims = context.getClaims();
    var user = getAppUserFromContext(context);
    if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
      claims.expiresAt(Instant.now().plusSeconds(60L));
      claims.claim("id", user.getLocalId());
      claims.claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
    }
  }

  private AppUser getAppUserFromContext(JwtEncodingContext context) {
    var auth = Objects.requireNonNull(context.get(OAuth2Authorization.class));
    var token = (UsernamePasswordAuthenticationToken) auth.getAttribute(Principal.class.getName());
    return (AppUser) Objects.requireNonNull(token).getPrincipal();
  }
}
