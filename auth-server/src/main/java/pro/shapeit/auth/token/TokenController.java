package pro.shapeit.auth.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TokenController {
  @Value("${REFRESH_TOKEN_COOKIE_NAME:refresh_token}")
  private String refreshTokenCookieName;

  @GetMapping("/refresh-token")
  ResponseEntity<?> getRefreshToken(@RequestBody Map<String, String> body) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(body);
  }
}
