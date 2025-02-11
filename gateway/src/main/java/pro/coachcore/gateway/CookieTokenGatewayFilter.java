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
public class CookieTokenGatewayFilter implements GatewayFilter {
  @Value("${ACCESS_TOKEN_KEY:accessToken}")
  private String accessTokenKey;

  @Value("${REFRESH_TOKEN_KEY:refreshToken}")
  private String refreshTokenKey;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    var accessToken = exchange.getRequest().getCookies().getFirst(accessTokenKey);

    if (accessToken == null) {
      return chain.filter(exchange);
    }

    var modifiedRequest = exchange.getRequest().mutate()
        .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(accessToken.getValue()))
        .build();
    var modifiedExchange = exchange.mutate()
        .request(modifiedRequest)
        .build();
    log.info("Token value: {}", accessToken.getValue());
    return chain.filter(modifiedExchange);
  }
}
