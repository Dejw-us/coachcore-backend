package pro.shapeit.training.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import pro.shapeit.training.plan.TrainingPlanService;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class TrainingPlanAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
  private final TrainingPlanService trainingPlanService;

  @Override
  public AuthorizationDecision check(
      Supplier<Authentication> authentication,
      RequestAuthorizationContext context
  ) {
    var planId = getTrainingPlanId(context);
    var auth = authentication.get();

    if (!auth.isAuthenticated() || !(auth instanceof JwtAuthenticationToken jwt)) {
      return new AuthorizationDecision(false);
    }

    var userId = (String) jwt.getToken().getClaim("id");
    var isOwner = trainingPlanService.isTrainingPlanOwner(userId, planId);

    return new AuthorizationDecision(isOwner);
  }

  private String getTrainingPlanId(RequestAuthorizationContext context) {
    return context.getRequest().getRequestURI().split("/")[3];
  }
}
