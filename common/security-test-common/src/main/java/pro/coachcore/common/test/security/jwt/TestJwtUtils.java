package pro.coachcore.common.test.security.jwt;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestJwtUtils {
  public static SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor createJwtPostProccessor(
      String userId, String... authorities) {
    return SecurityMockMvcRequestPostProcessors.jwt().jwt(jwt -> {
      jwt.claim("id", userId);
      jwt.claim("roles", authorities);
    });
  }
}
