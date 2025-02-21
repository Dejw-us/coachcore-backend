package pro.coachcore.newsletter;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pro.coachcore.dto.SendEmailDto;
import pro.coachcore.newsletter.subscription.Subscription;
import pro.coachcore.newsletter.subscription.SubscriptionService;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/newsletter")
@RequiredArgsConstructor
public class NewsletterController {
  private final WebClient webClient;
  private final SubscriptionService subscriptionService;

  @PostMapping(value = "/send", produces = "application/json")
  ResponseEntity<String> sendNewsletter(
      @RequestBody @Valid SendNewsletterDto dto) {
    var emails = subscriptionService.getSubscriptions(dto.language()).stream()
        .map(Subscription::getEmail)
        .toList();
    var body = new SendEmailDto(
        "newsletter@coachcore.pro",
        emails,
        dto.subject(),
        dto.content());
    var responseBody = webClient.post()
        .uri("/email/send")
        .contentType(APPLICATION_JSON)
        .bodyValue(body)
        .retrieve()
        .bodyToMono(String.class)
        .block();
    return ResponseEntity
        .ok(responseBody);
  }
}
