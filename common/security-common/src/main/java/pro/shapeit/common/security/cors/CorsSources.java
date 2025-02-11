package pro.shapeit.common.security.cors;

import lombok.experimental.UtilityClass;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@UtilityClass
public class CorsSources {
  public static CorsConfigurationSource enableReactClientCorsConfigurationSource() {
    var config = new CorsConfiguration();

    config.setAllowedOrigins(List.of("*"));
    config.addAllowedMethod("*");
    config.addAllowedHeader("*");
    config.addExposedHeader("Location");
    config.setAllowCredentials(true);

    var source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", config);

    return source;
  }
}
