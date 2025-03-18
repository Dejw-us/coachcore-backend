package pro.coachcore.training.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import pro.coachcore.lang.message.MessageService;

@Configuration
public class I18NConfig {
  @Bean
  MessageService messageService() {
    var source = new ResourceBundleMessageSource();
    source.setBasenames("i18n/messages");
    source.setDefaultEncoding("UTF-8");
    source.setUseCodeAsDefaultMessage(true);
    source.setCacheSeconds(3600);

    return new MessageService(source);
  }
}
