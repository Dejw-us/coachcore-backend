package pro.coachcore.gateway;

import lombok.RequiredArgsConstructor;
import pro.coachcore.gateway.service.ServicesProperites;

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
  private final ServicesProperites servicesProperites;
  private final CookieAccessTokenGatewayFilter cookieTokenGatewayFilter;
  private final CookieRefreshTokenGatewayFilter cookieRefreshTokenGatewayFilter;

  @Bean
  RouteLocator routeLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("training-plans", configureTrainingApiRoute("/training-plans/**"))
        .route("catalog-exercises",
        configureTrainingApiRoute("/catalog-exercises/**"))
        .route("exercise-categories",
        configureTrainingApiRoute("/exercise-categories/**"))
        .route("oauth2-refresh-token", route -> route
            .path("/oauth2/token")
            .filters(filters -> filters.filter(cookieRefreshTokenGatewayFilter))
            .uri(servicesProperites.oauth2ServerUrl()))
        .route("newsletter-service", route -> route
            .path("/newsletter/**")
            .filters(cookieTokenGatewayFilter.asFunctionFilter())
            .uri(servicesProperites.newsletterServiceUrl()))
        // .route("users", configureApiRoute("/users/**",
        // servicesProperites.oauth2ServerUrl()))
        .build();
  }

  @Bean
  CorsWebFilter corsWebFilter() {
    var config = new CorsConfiguration();

    config.setAllowedOrigins(List.of("http://localhost:3000"));
    config.setAllowedMethods(List.of("*"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);

    var source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", config);

    return new CorsWebFilter(source);
  }

  private Function<PredicateSpec, Buildable<Route>> configureTrainingApiRoute(String path) {
    return configureApiRoute(path, servicesProperites.trainingServiceUrl());
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
