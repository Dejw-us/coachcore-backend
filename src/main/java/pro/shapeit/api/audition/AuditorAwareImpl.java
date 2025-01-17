package pro.shapeit.api.audition;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import pro.shapeit.api.common.util.JwtUtils;

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
