package pro.coachcore.email;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public record EmailData(
    String from,
    List<String> to,
    String subject,
    String content) {
  public MimeMessage compose(JavaMailSender mailSender) throws MessagingException {
    var mime = mailSender.createMimeMessage();
    var helper = new MimeMessageHelper(mime);

    helper.setFrom(from);
    helper.setTo(to.toArray(String[]::new));
    helper.setSubject(subject);
    helper.setText(content, true);

    return mime;
  }
}
