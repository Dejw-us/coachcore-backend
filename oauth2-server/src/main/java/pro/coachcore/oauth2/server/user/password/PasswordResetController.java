package pro.coachcore.oauth2.server.user.password;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.exception.ResourceNotFoundException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/password/reset")
public class PasswordResetController {
  private final PasswordService passwordService;

  @GetMapping
  String index(@RequestParam String token, Model model) {
    var dto = new ResetPasswordDto();
    dto.setToken(token);
    model.addAttribute("resetPassword", dto);
    return "account/reset-password";
  }

  @GetMapping("/generate")
  String showGenerateLinkPage() {
    return "account/generate-link";
  }

  @PostMapping("/generate")
  String generateResetLink(@RequestParam String email, Model model) {
    var token = passwordService.generateToken(email);
    passwordService.sendResetLink(token, email);
    model.addAttribute("message", "Check your inbox. Remember to also check for spam.");
    return "account/generate-link";
  }

  @PatchMapping
  String resetPassword(
      @Valid @ModelAttribute("resetPassword") ResetPasswordDto dto,
      BindingResult errors) {
    if (!dto.passwordsMatch()) {
      errors.rejectValue("password", "error.password", "Passwords do not match");
      return "account/reset-password";
    }

    try {
      var token = passwordService.getToken(dto.getToken());
      passwordService.resetPassword(dto.getPassword(), token);
    } catch (ResourceNotFoundException exception) {
      return "account/reset-password";
    }

    return "account/reset-password";
  }
}
