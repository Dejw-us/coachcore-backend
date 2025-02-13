package pro.coachcore.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CookieAccessTokenGatewayFilter implements GatewayFilter {
  @Value("${ACCESS_TOKEN_KEY:access_token}")
  private String accessTokenKey;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    log.info("starting filter");
    var cookies = exchange.getRequest().getCookies();
    var accessTokenCookie = cookies.getFirst(accessTokenKey);

    if (accessTokenCookie == null) {
      log.info("access token == null");
      return chain.filter(exchange);
    }

    var modifiedRequest = exchange.getRequest().mutate()
        .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(accessTokenCookie.getValue()))
        .build();
    var modifiedExchange = exchange.mutate()
        .request(modifiedRequest)
        .build();
    log.info("Token value: {}", accessTokenCookie.getValue());

    return chain.filter(modifiedExchange);
  }
}
