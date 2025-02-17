package pro.coachcore.newsletter.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
  private static final String NEWSLETTER_MAIL_NAME = "newsletter@coachcore.pro";

  private final JavaMailSender javaMailSender;

  public void sendEmail(String to, String messageHtml, String subject) throws MessagingException {
    var mime = javaMailSender.createMimeMessage();
    var helper = new MimeMessageHelper(mime);

    helper.setFrom(NEWSLETTER_MAIL_NAME);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(messageHtml, true);

    javaMailSender.send(mime);
  }
}
