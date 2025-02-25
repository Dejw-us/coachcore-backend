package pro.coachcore.oauth2.server.user.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.oauth2.server.user.UserService;
import pro.coachcore.exception.ResourceNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserAccountController {
  private final UserService userService;

  @GetMapping("/account/login")
  String getLoginPage() {
    log.info("Login");
    return "account/login";
  }

  @GetMapping("/account/register")
  String getRegisterPage(Model model) {
    model.addAttribute("registerUser", new RegisterUserDto());
    return "account/register";
  }

  @PostMapping("/account/register")
  String postRegister(
      @Valid @ModelAttribute("registerUser") RegisterUserDto dto,
      Errors errors,
      Model model) throws ResourceNotFoundException {
    if (errors.hasErrors()) {
      return "account/register";
    }
    userService.registerUser(dto);
    return "redirect:/account/login";
  }
}
