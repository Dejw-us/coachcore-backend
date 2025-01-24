package pro.shapeit.auth.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pro.shapeit.exception.ResourceNotFoundException;

@Controller
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/account/login")
  String getLoginPage() {
    return "account/login";
  }

  @GetMapping("/account/register")
  String getRegisterPage(Model model) {
    model.addAttribute("registerUser", new RegisterUserDto());
    return "account/register";
  }

  @PostMapping("/account/register")
  String postRegister(@Valid @ModelAttribute("registerUser") RegisterUserDto dto) throws ResourceNotFoundException {
    userService.registerUser(dto);
    return "redirect:/account/login";
  }
}
