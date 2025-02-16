package pro.shapeit.newsletter.admin;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;
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
      @RequestParam String subject,
      @RequestParam String language,
      RedirectAttributes redirectAttributes
  ) {
    if (StringUtils.isEmpty(message) || StringUtils.isEmpty(subject)) {
      redirectAttributes.addFlashAttribute("error", "You cannot send empty email");
      return "redirect:/admin/newsletter/compose";
    }
    for (var subscription : subscriptionService.getSubscriptions(language)) {
      var code = subscriptionService.getOrGenerateUnsubscribeCode(subscription);
      var cancelMessage = subscription.getLanguage().equals("pl") ? "Zrezygnuj z subskrypcji: " : "Cancel Subscription ";
      var unsubscribeLink = host.concat("/newsletter/subscription/cancel?code=").concat(code.getCode());
      var finalMessage = message.concat("<br/>").concat(cancelMessage).concat(unsubscribeLink);

      try {
        emailService.sendEmail(subscription.getEmail(), finalMessage, subject);
      } catch (MailException | MessagingException exception) {
        redirectAttributes.addFlashAttribute("error", exception.getMessage());
        return "redirect:/admin/newsletter/compose";
      }
    }
    log.info("Sending email. Subject: {}, message: {}", subject, message);
    redirectAttributes.addFlashAttribute("message", "Newsletter has been sent");
    return "redirect:/admin/subscriptions";
  }
}
