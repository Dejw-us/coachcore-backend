package pro.coachcore.newsletter.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2-mail-client")
public record OAuth2MailClientProperties(
  String clientId,
  String clientSecret
) {
}
