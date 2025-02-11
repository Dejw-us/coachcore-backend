package pro.coachcore.gateway;

import java.util.List;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import pro.shapeit.common.security.cors.CorsSources;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
  @Value("${TRAINING_API_URI:http://localhost:8082}")
  private String trainingApiUri;

  @Value("${AUTH_SERVER_URI:http://localhost:9000}")
  private String authServerUri;

  private final CookieTokenGatewayFilter cookieTokenGatewayFilter;

  @Bean
  RouteLocator routeLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("training-plans", configureTrainingApiRoute("/training-plans/**"))
        .route("catalog-exercises", configureTrainingApiRoute("/catalog-exercises/**"))
        .route("exercise-categories", configureTrainingApiRoute("/exercise-categories/**"))
        .route("account", route -> route
            .path("/account/**")
            .uri(authServerUri))
        .route("users", configureApiRoute("/users/**", authServerUri))
        .build();
  }

  @Bean
  CorsWebFilter corsWebFilter() {
    var config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOriginPatterns(List.of("*"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return new CorsWebFilter(source);
  }

  private Function<GatewayFilterSpec, UriSpec> addV1Prefix() {
    return filters -> filters.prefixPath("/v1");
  }

  private Function<PredicateSpec, Buildable<Route>> configureTrainingApiRoute(String path) {
    return configureApiRoute(path, trainingApiUri);
  }

  private Function<PredicateSpec, Buildable<Route>> configureApiRoute(String path, String uri) {
    return route -> route
        .path(path)
        .filters(addV1Prefix())
        .uri(uri);
  }
}
