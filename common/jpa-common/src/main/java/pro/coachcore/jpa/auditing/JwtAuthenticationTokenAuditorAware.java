package pro.coachcore.jpa.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class JwtAuthenticationTokenAuditorAware implements AuditorAware<String> {
  @Override
  public @NonNull Optional<String> getCurrentAuditor() {
    var auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null) {
      log.debug("auth == null");
      return Optional.empty();
    }
    if (!auth.isAuthenticated() || !(auth instanceof JwtAuthenticationToken jwt)) {
      log.debug("User is not authenticated");
      return Optional.empty();
    }

    var userId = (String) jwt.getToken().getClaim("id");

    if (userId == null) {
      log.debug("User id is null");
    } else {
      log.debug("Extracted user id from jwt: {}", userId);
    }

    return Optional.ofNullable(userId);
  }
}
