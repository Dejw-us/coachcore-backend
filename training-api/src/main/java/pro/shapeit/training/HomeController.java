package pro.shapeit.training;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @GetMapping("/home")
  String home(@AuthenticationPrincipal Jwt jwt) {
    return "jwt: " + jwt.getClaims();
  }
}
