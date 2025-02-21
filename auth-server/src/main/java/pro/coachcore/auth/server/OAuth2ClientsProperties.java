package pro.coachcore.auth.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class OAuth2ClientsProperties {
  @Value("${OAUTH2_MAIL_CLIENT_ID}")
  private String mailClientId;

  @Value("${OAUTH2_MAIL_CLIENT_SECRET}")
  private String mailClientSecret;

  @Value("${OAUTH2_WEB_APP_CLIENT_ID}")
  private String webAppClientId;

  @Value("${OAUTH2_WEB_APP_CLIENT_SECRET}")
  private String webAppClientSecret;
}
