package pro.coachcore.oauth2.server.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.coachcore.lang.message.MessageService;

@Configuration
public class I18NConfig {
  @Bean
  MessageService messageService() {
    return MessageService.withDefaultSource();
  }
}
