package pro.shapeit.newsletter.subscription;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.dto.MessageDto;

@RestController
@RequestMapping("/newsletter")
@RequiredArgsConstructor
public class SubscriptionController {
  private final SubscriptionService subscriptionService;
  private final JavaMailSender mailSender;

  @PostMapping("/subscribe")
  ResponseEntity<MessageDto> subscribe(
      @RequestBody CreateSubscriptionDto dto
  ) throws BadRequestException {
    subscriptionService.subscribe(dto);
    return ResponseEntity
        .ok(new MessageDto("Subscribed"));
  }

  @PostMapping("/unsubscribe")
  ResponseEntity<MessageDto> unsubscribe(
      @RequestParam String code
  ) throws BadRequestException {
    subscriptionService.unsubscribe(code);
    return ResponseEntity
        .ok(new MessageDto("Unsubscribed"));
  }
}
