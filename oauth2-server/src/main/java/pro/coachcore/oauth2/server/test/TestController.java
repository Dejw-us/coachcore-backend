package pro.coachcore.oauth2.server.test;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {
  private final RestClient restClient;

  @GetMapping("/test")
  ResponseEntity<String> testEmail() {
    var response = restClient.get()
        .uri("/email/send")
        .attributes(clientRegistrationId("mail-client"))
        .retrieve()
        .body(String.class);
    return ResponseEntity.ok(response);
  }
}
