package pro.shapeit.auth.user;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuditorAware implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      return Optional.empty();
    }
    return Optional.of(getUserLocalIdFromAuth(auth));
  }

  private String getUserLocalIdFromAuth(Authentication auth) {
    return "guest"; // TODO fetch user local id
  }
}
