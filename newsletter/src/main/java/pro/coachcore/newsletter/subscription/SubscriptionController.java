package pro.coachcore.newsletter.subscription;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pro.coachcore.dto.MessageDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsletter")
@RequiredArgsConstructor
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  @PostMapping("/subscribe")
  ResponseEntity<MessageDto> subscribe(
      @RequestBody @Valid CreateSubscriptionDto dto
  ) {
    subscriptionService.subscribe(dto);
    return ResponseEntity
        .ok(new MessageDto("Subscribed"));
  }

  @PostMapping("/unsubscribe")
  ResponseEntity<MessageDto> unsubscribe(
      @RequestParam String code
  ) {
    subscriptionService.unsubscribe(code);
    return ResponseEntity
        .ok(new MessageDto("Unsubscribed"));
  }
}
