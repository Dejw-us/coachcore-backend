package pro.coachcore.email;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
  private final EmailService emailService;

  @PostMapping("/send")
  ResponseEntity<EmailResult> postMethodName(@RequestBody EmailData emailData) {
    var result = emailService.sendEmail(emailData);
    return ResponseEntity
        .ok(result);
  }
}
