package pro.shapeit.auth.user;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuditorAware implements AuditorAware<String> {
  @Override
  public @NonNull Optional<String> getCurrentAuditor() {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      return Optional.empty();
    }
    return getUserLocalIdFromAuth(auth);
  }

  private Optional<String> getUserLocalIdFromAuth(Authentication auth) {
    try {
      return Optional.of(((AppUser) auth.getPrincipal()).getLocalId());
    } catch (ClassCastException ignore) {
      return Optional.empty();
    }
  }
}
