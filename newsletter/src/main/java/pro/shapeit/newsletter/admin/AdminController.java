package pro.shapeit.newsletter.admin;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pro.shapeit.newsletter.email.EmailService;
import pro.shapeit.newsletter.subscription.SubscriptionService;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
  @Value("${HOST:http://localhost:8000}")
  private String host;

  private final SubscriptionService subscriptionService;
  private final EmailService emailService;

  @GetMapping("/subscriptions")
  String getSubscriptions(Model model) {
    model.addAttribute("subscriptions", subscriptionService.getSubscriptions());
    return "subscriptions";
  }

  @GetMapping("/newsletter/compose")
  String showComposePage() {
    return "compose-newsletter";
  }

  @PostMapping("/newsletter/send")
  String sendNewsletter(
      @RequestParam String message,
      @RequestParam String subject
  ) throws MessagingException {
    for (var subscription : subscriptionService.getSubscriptions()) {
      var code = subscriptionService.getOrGenerateUnsubscribeCode(subscription);
      var unsubscribeLink = host.concat("/newsletter/subscription/cancel?code=").concat(code.getCode());
      var finalMessage = message.concat("<br/> Cancel subscription: ").concat(unsubscribeLink);

      emailService.sendEmail(subscription.getEmail(), finalMessage, subject);
    }
    log.info("Sending email. Subject: {}, message: {}", subject, message);
    return "redirect:/admin/subscriptions";
  }
}
