package pro.shapeit.common.security.cors;

import lombok.experimental.UtilityClass;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@UtilityClass
public class CorsSources {
  public static CorsConfigurationSource enableReactClientCorsConfigurationSource() {
    var config = new CorsConfiguration();

    config.addAllowedOrigin("http://localhost:3000");
    config.addAllowedMethod("*");
    config.addAllowedHeader("*");
    config.addExposedHeader("Location");
    config.setAllowCredentials(true);

    var source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", config);

    return source;
  }
}
