package pro.coachcore.gateway;

import lombok.extern.slf4j.Slf4j;
import pro.coachcore.oauth2.common.CookieTokensNames;

import java.util.function.Function;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.http.HttpHeaders; 
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CookieAccessTokenGatewayFilter implements GatewayFilter {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    var cookies = exchange.getRequest().getCookies();
    var accessTokenCookie = cookies.getFirst(CookieTokensNames.ACCESS_TOKEN);
    log.debug("Filtering: {}", exchange.getRequest().getPath().value());
    if (accessTokenCookie == null) {
      log.debug("No access token cookie found");
      return chain.filter(exchange);
    }
    log.debug("Found access token cookie");
    var modifiedRequest = exchange.getRequest().mutate()
        .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(accessTokenCookie.getValue()))
        .build();
    log.debug("Added access token header");
    var modifiedExchange = exchange.mutate()
        .request(modifiedRequest)
        .build();

    return chain.filter(modifiedExchange);
  }

  public Function<GatewayFilterSpec, UriSpec> asFunctionFilter() {
    return filters -> filters.filter(this);
  }
}
