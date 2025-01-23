package pro.shapeit.training;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @GetMapping("/home")
  String home(
      @RequestParam String code
  ) {
    var tokenUri = "http://localhost:9000/oauth2/token";
    var redirectUri = "http://localhost:8080/home";
    var clientId = "shapeit";
    var clientSecret = "shapeit";

    return "Code: " + code;
  }
}
