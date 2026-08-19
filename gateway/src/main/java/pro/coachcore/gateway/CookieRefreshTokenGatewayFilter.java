package pro.coachcore.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.oauth2.common.CookieTokensNames;

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

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieRefreshTokenGatewayFilter implements GatewayFilter {
  private final ModifyRequestBodyGatewayFilterFactory factory;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    return Optional.ofNullable(exchange.getRequest().getCookies().getFirst(CookieTokensNames.REFRESH_TOKEN))
        .map(HttpCookie::getValue)
        .map(refreshToken -> {
          log.debug("Setting new body on request: {}", exchange.getRequest().getPath().value());
          var newBody = "grant_type=refresh_token&refresh_token=".concat(refreshToken);
          return factory.apply(config -> config
              .setContentType(MediaType.APPLICATION_FORM_URLENCODED.toString())
              .setRewriteFunction(String.class, String.class, (_, _) -> Mono.just(newBody)))
              .filter(exchange, chain);
        })
        .orElse(chain.filter(exchange));
  }
}
