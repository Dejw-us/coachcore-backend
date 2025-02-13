package pro.coachcore.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
  @Value("${TRAINING_API_URI:http://localhost:8082}")
  private String trainingApiUri;

  @Value("${AUTH_SERVER_URI:http://localhost:9000}")
  private String authServerUri;

  private final CookieAccessTokenGatewayFilter cookieTokenGatewayFilter;

  @Bean
  RouteLocator routeLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("training-plans", configureTrainingApiRoute("/training-plans/**"))
        .route("catalog-exercises", configureTrainingApiRoute("/catalog-exercises/**"))
        .route("exercise-categories", configureTrainingApiRoute("/exercise-categories/**"))
        .build();
  }

  @Bean
  CorsWebFilter corsWebFilter() {
    var config = new CorsConfiguration();

    config.setAllowedOrigins(List.of("http://localhost:3000"));
    config.setAllowedMethods(List.of("POST", "GET", "PATCH", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);

    var source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", config);

    return new CorsWebFilter(source);
  }

  private Function<PredicateSpec, Buildable<Route>> configureTrainingApiRoute(String path) {
    return configureApiRoute(path, trainingApiUri);
  }

  private Function<PredicateSpec, Buildable<Route>> configureApiRoute(String path, String uri) {
    return route -> route
        .path(path)
        .filters(filters -> filters
            .prefixPath("/v1")
            .filter(cookieTokenGatewayFilter))
        .uri(uri);
  }
}
