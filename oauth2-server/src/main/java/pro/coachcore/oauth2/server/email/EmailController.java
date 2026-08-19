package pro.coachcore.oauth2.server.email;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.dto.SendEmailDto;

@Slf4j
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
  private final EmailService emailService;

  @PostMapping("/send")
  ResponseEntity<EmailResult> postMethodName(@RequestBody SendEmailDto dto) {
    var result = emailService.sendEmail(dto);
    return ResponseEntity.ok(result);
  }
}
