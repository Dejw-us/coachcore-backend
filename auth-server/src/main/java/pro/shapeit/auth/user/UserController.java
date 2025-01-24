package pro.shapeit.auth.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
  @GetMapping("/account/login")
  String getLoginPage() {
    return "account/login";
  }

  @GetMapping("/account/register")
  String getRegisterPage() {
    return "account/register";
  }
}
