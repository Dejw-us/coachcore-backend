package pro.coachcore.auth.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2-clients")
public record OAuth2ClientsProperties(
  String mailClientId,
  String mailClientSecret,
  String webAppClientId,
  String webAppClientSecret
) {
}
