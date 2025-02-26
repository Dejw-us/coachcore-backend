package pro.coachcore.newsletter;

import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/newsletter")
@RequiredArgsConstructor
public class NewsletterController {
  private final NewsletterService newsletterService;

  @PostMapping(value = "/send", produces = "application/json")
  ResponseEntity<NewsletterSendResponse> sendNewsletter(
      @RequestBody @Valid SendNewsletterDto dto) {
    return ResponseEntity
        .ok(newsletterService.sendNewsletter(dto));
  }
}
