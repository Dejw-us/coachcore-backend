package pro.coachcore.newsletter.client;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
  private final OAuth2MailClientProperties oAuth2MailClientProperties;

  @Bean
  WebClient webClient() {
    return WebClient.builder()
        .baseUrl("http://localhost:8080")
        .filter(basicAuthentication(oAuth2MailClientProperties.clientId(), oAuth2MailClientProperties.clientSecret()))
        .build();
  }
}
