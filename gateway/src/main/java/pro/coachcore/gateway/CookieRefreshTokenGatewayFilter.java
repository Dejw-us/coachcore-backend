package pro.coachcore.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CookieRefreshTokenGatewayFilter implements GatewayFilter {
  @Value("${REFRESH_TOKEN_KEY:refresh_token}")
  private String refreshTokenKey;

  private final ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (exchange.getRequest().getHeaders().getFirst("Cookie-Refresh-Token") == null) {
      return chain.filter(exchange);
    }
    return Optional.ofNullable(exchange.getRequest().getCookies().getFirst(refreshTokenKey))
        .map(HttpCookie::getValue)
        .map(refreshToken -> {
          var newBody = "grant_type=refresh_token&refresh_token=" + refreshToken;
          return modifyRequestBodyGatewayFilterFactory.apply(config -> config
              .setContentType(MediaType.APPLICATION_FORM_URLENCODED.toString())
              .setRewriteFunction(String.class, String.class, (webExchange, body) -> Mono.just(newBody)))
              .filter(exchange, chain);
        })
        .orElse(chain.filter(exchange));
  }
}
