package pro.coachcore.gateway;

import lombok.extern.slf4j.Slf4j;
import pro.coachcore.oauth2.common.CookieTokensNames;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CookieAccessTokenGatewayFilter implements GatewayFilter {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    var cookies = exchange.getRequest().getCookies();
    var accessTokenCookie = cookies.getFirst(CookieTokensNames.ACCESS_TOKEN);

    if (accessTokenCookie == null) {
      return chain.filter(exchange);
    }

    var modifiedRequest = exchange.getRequest().mutate()
        .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(accessTokenCookie.getValue()))
        .build();
    var modifiedExchange = exchange.mutate()
        .request(modifiedRequest)
        .build();

    return chain.filter(modifiedExchange);
  }
}
