package pro.shapeit.util;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

public class HttpUtils {
  public static void anyAuthenticated(
      AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry
  ) {
    registry.anyRequest().authenticated();
  }
}
