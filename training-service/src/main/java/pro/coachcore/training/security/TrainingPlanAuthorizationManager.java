package pro.coachcore.training.security;

import lombok.RequiredArgsConstructor;
import pro.coachcore.training.plan.TrainingPlanService;
import pro.coachcore.training.plan.owner.TrainingPlanOwnerService;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class TrainingPlanAuthorizationManager
    implements AuthorizationManager<RequestAuthorizationContext> {
  private final AuditorAware<String> auditorAware;
  private final TrainingPlanOwnerService planOwnerService;

  @Override
  public AuthorizationDecision check(Supplier<Authentication> authentication,
      RequestAuthorizationContext context) {
    var planId = getTrainingPlanId(context);
    var auth = authentication.get();

    if (!auth.isAuthenticated() || !(auth instanceof JwtAuthenticationToken)) {
      return new AuthorizationDecision(false);
    }

    var userId = auditorAware.getCurrentAuditor()
        .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("user id not found"));
    var canView = planOwnerService.canView(planId, userId);

    return new AuthorizationDecision(canView);
  }

  private String getTrainingPlanId(RequestAuthorizationContext context) {
    return context.getRequest().getRequestURI().split("/")[3];
  }
}
