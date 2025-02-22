package pro.coachcore.newsletter.subscription;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pro.coachcore.dto.MessageDto;

@RestController
@RequestMapping("/newsletter")
@RequiredArgsConstructor
public class SubscriptionController {
  private final SubscriptionService subscriptionService;
  private final SubscriptionMapper subscriptionMapper;

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/subscriptions")
  public ResponseEntity<List<SubscriptionDto>> getSubscriptions(
      @RequestParam(required = false) String language) {
    var subscriptions = subscriptionService.getSubscriptions(language);

    return ResponseEntity
        .ok(subscriptionMapper.map(subscriptions));
  }

  @PostMapping("/subscribe")
  ResponseEntity<MessageDto> subscribe(
      @RequestBody @Valid CreateSubscriptionDto dto) {
    subscriptionService.subscribe(dto);
    return ResponseEntity
        .ok(new MessageDto("Subscribed"));
  }

  @PostMapping("/unsubscribe")
  ResponseEntity<MessageDto> unsubscribe(
      @RequestParam String code) {
    subscriptionService.unsubscribe(code);
    return ResponseEntity
        .ok(new MessageDto("Unsubscribed"));
  }
}
