package pro.coachcore.lang.message;

import java.util.Locale;
import java.util.Objects;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageService {
  private final MessageSource messages;

  public String getMessage(String key, Object... args) {
    var locale = Objects.requireNonNullElse(LocaleContextHolder.getLocale(), Locale.US);
    return messages.getMessage(key, args, locale);
  }

  public static MessageService withDefaultSource() {
    var source = new ResourceBundleMessageSource();
    source.setBasenames("i18n/messages");
    source.setDefaultEncoding("UTF-8");
    source.setUseCodeAsDefaultMessage(true);
    source.setCacheSeconds(3600);

    return new MessageService(source);
  }
}
