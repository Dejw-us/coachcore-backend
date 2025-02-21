package pro.coachcore.newsletter.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
  @Bean
  WebClient webClient(OAuth2AuthorizedClientManager manager) {
    var oauth2Client = new ServletOAuth2AuthorizedClientExchangeFilterFunction(manager);

    oauth2Client.setDefaultClientRegistrationId("mail-client");

    return WebClient.builder()
        .baseUrl("http://localhost:8080")
        .filter((request, next) -> {
          log.info("Sending request to {}", request.url());
          return next.exchange(request);
        })
        .apply(oauth2Client.oauth2Configuration())
        .build();
  }
}
