package pro.shapeit.backend.audition;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pro.shapeit.backend.common.util.JwtUtils;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    var auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !auth.isAuthenticated()) {
      return Optional.empty();
    }

    return Optional.ofNullable(JwtUtils.extractUserIdFromAuth(auth));
  }
}
