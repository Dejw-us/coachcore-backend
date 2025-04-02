package pro.coachcore.oauth2.server.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.dto.SendEmailDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
  @Value("${dev-mode.enabled}")
  private Boolean devMode;

  private final JavaMailSender mailSender;

  public EmailResult sendEmail(SendEmailDto dto) {
    if (devMode) {
      return new EmailResult(EmailStatus.DEV_SENT, dto.to().size());
    }
    try {
      var email = compose(mailSender, dto);

      mailSender.send(email);

      return new EmailResult(EmailStatus.SENT_ALL, dto.to().size());
    } catch (Exception exception) {
      log.debug("Failed to send email: {}", exception.getMessage());
      return new EmailResult(EmailStatus.FAILED, 0);
    }
  }

  private MimeMessage compose(JavaMailSender mailSender, SendEmailDto dto)
      throws MessagingException {
    var mime = mailSender.createMimeMessage();
    var helper = new MimeMessageHelper(mime);

    helper.setFrom(dto.from());
    helper.setTo(dto.to().toArray(String[]::new));
    helper.setSubject(dto.subject());
    helper.setText(dto.content(), true);

    return mime;
  }
}
