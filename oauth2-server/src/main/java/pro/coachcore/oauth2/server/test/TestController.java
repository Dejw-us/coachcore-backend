package pro.coachcore.oauth2.server.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pro.coachcore.dto.SendEmailDto;
import pro.coachcore.oauth2.server.email.EmailService;

@RestController
@RequiredArgsConstructor
public class TestController {
  private final EmailService emailService;

  @GetMapping("/test")
  ResponseEntity<String> testEmail(@RequestBody SendEmailDto dto) {
    emailService.sendEmail(dto);
    return ResponseEntity.ok("ok");
  }
}
