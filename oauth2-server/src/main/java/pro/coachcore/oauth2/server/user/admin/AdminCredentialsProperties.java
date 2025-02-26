package pro.coachcore.oauth2.server.user.admin;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin")
public record AdminCredentialsProperties(
  String username,
  String password
) {
}
