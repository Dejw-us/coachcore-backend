package pro.shapeit.training.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
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
    var request = context.getRequest();
    var planId = extractPathVariable(request.getRequestURI(), "/v1/training-plans/", "/units");
    var auth = authentication.get();

    if (planId == null || !auth.isAuthenticated()) {
      if (planId == null) {
        System.out.println("pro.shapeit.plan id == null");
      }
      if (!auth.isAuthenticated()) {
        System.out.println("no auth");
      }
      return new AuthorizationDecision(false);
    }

    var userId = "test";
    var isOwner = trainingPlanService.isTrainingPlanOwner(userId, planId);
    System.out.println("auth");
    return new AuthorizationDecision(isOwner);
  }

  private String extractPathVariable(String requestUri, String prefix, String suffix) {
    if (requestUri.startsWith(prefix) && requestUri.contains(suffix)) {
      return requestUri.substring(
          prefix.length(),
          requestUri.indexOf(suffix)
      );
    }
    return null;
  }
}
