package pro.coachcore.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
  private final JavaMailSender mailSender;

  public EmailResult sendEmail(EmailData data) {
    try {
      var email = data.compose(mailSender);

      mailSender.send(email);

      return new EmailResult(EmailStatus.SENT_ALL, data.to().size());
    } catch (Exception ignore) {
      log.info("exception type: {}", ignore.getClass().getName());
      return new EmailResult(EmailStatus.FAILED, 0);
    }
  }
}
