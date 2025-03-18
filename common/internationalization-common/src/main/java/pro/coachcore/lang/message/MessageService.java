package pro.coachcore.lang.message;

import java.util.Locale;
import java.util.Objects;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageService {
  private final MessageSource messages;

  public String getMessage(String key, Object... args) {
    var locale = Objects.requireNonNullElse(LocaleContextHolder.getLocale(), Locale.US);
    return messages.getMessage(key, args, locale);
  }
}
