package pro.shapeit.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import pro.shapeit.api.common.util.JwtUtils;
import pro.shapeit.api.training.plan.TrainingPlanService;

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
    var planId = context.getRequest().getParameter("planId");
    var auth = authentication.get();

    if (planId == null || !auth.isAuthenticated()) {
      return new AuthorizationDecision(false);
    }

    var userId = JwtUtils.extractUserIdFromAuth(auth);
    var isOwner = trainingPlanService.isTrainingPlanOwner(userId, planId);

    return new AuthorizationDecision(isOwner);
  }
}
