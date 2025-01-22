package pro.shapeit.common.audition;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import pro.shapeit.common.util.JwtUtils;

import java.util.Optional;

public class KeycloakAuditorAware implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    var auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !auth.isAuthenticated()) {
      return Optional.empty();
    }

    return Optional.ofNullable(JwtUtils.extractUserIdFromAuth(auth));
  }
}
