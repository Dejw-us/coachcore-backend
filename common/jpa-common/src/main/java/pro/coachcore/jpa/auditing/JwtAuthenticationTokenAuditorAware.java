package pro.shapeit.jpa.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

public class JwtAuthenticationTokenAuditorAware implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    var auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null) {
      return Optional.empty();
    }
    if (!auth.isAuthenticated() || !(auth instanceof JwtAuthenticationToken jwt)) {
      return Optional.empty();
    }

    var userId = (String) jwt.getToken().getClaim("id");

    return Optional.of(userId);
  }
}
