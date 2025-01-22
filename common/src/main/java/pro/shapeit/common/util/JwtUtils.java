package pro.shapeit.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@UtilityClass
public class JwtUtils {
  public static String extractUserIdFromAuth(Authentication auth) {
    if (!(auth instanceof JwtAuthenticationToken jwtAuth)) {
      throw new AuthenticationCredentialsNotFoundException("You must be authenticated to perform this action");
    }
    return jwtAuth.getToken().getClaim("sub");
  }
}
