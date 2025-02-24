package pro.coachcore.auth.user.account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.auth.user.RegisterUserDto;
import pro.coachcore.auth.user.UserService;
import pro.coachcore.exception.ResourceNotFoundException;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserAccountController {
  private final UserService userService;
  private final RequestCache cache = new HttpSessionRequestCache();

  @GetMapping("/account/login")
  String getLoginPage(HttpServletRequest request, HttpServletResponse response) {
    var savedRequest = cache.getRequest(request, response);
    if (savedRequest != null) {
      log.info("redirect: {}", savedRequest.getRedirectUrl());
    }
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
