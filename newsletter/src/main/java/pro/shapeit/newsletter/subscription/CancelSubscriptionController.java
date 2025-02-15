package pro.shapeit.newsletter.subscription;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/newsletter/subscription/cancel")
@RequiredArgsConstructor
public class CancelSubscriptionController {
  private final SubscriptionService subscriptionService;

  @GetMapping
  String showCancelSubscriptionPage() {
    return "cancel-subscription";
  }

  @PostMapping
  String cancelSubscription(
      @RequestParam String code
  ) throws BadRequestException {
    subscriptionService.unsubscribe(code);
    return "cancel-subscription-success";
  }
}
