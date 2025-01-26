package pro.shapeit.auth.token;

import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.ACCESS_TOKEN;

@Component
public class TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
  @Override
  public void customize(JwtEncodingContext context) {
    if (context.getTokenType().equals(ACCESS_TOKEN)) {
      var claims = context.getClaims();
      claims.expiresAt(Instant.now().plusSeconds(3600L * 24L));
    }
  }
}
