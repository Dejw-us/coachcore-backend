package pro.coachcore.newsletter;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import pro.coachcore.dto.SendEmailDto;
import pro.coachcore.newsletter.subscription.Subscription;
import pro.coachcore.newsletter.subscription.SubscriptionService;

@Service
@RequiredArgsConstructor
public class NewsletterService {
  private final WebClient webClient;
  private final SubscriptionService subscriptionService;

  public NewsletterSendResponse sendNewsletter(SendNewsletterDto dto) {
    return constructSendEmailDto(dto)
      .map(this::sendEmails)
      .orElse(new NewsletterSendResponse("NO_SUBSCRIPTIONS", 0));
  }

  private NewsletterSendResponse sendEmails(SendEmailDto dto) {
    return webClient.post()
        .uri("/email/send")
        .contentType(APPLICATION_JSON)
        .bodyValue(dto)
        .retrieve()
        .bodyToMono(NewsletterSendResponse.class)
        .block();
  }

  private Optional<SendEmailDto> constructSendEmailDto(SendNewsletterDto dto) {
    var emails = subscriptionService.getSubscriptions(dto.language()).stream()
        .map(Subscription::getEmail)
        .toList();
    if (emails.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(new SendEmailDto(
        "newsletter@coachcore.pro",
        emails,
        dto.subject(),
        dto.content()));
  }
}
