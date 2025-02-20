package pro.coachcore.newsletter.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oauth2-mail-client")
public record OAuth2MailClientProperties(
  String clientId,
  String clientSecret
) {
}
