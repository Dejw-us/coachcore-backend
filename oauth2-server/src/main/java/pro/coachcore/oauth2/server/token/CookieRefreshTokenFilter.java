package pro.coachcore.oauth2.server.token;

import static java.lang.String.format;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CookieRefreshTokenFilter extends OncePerRequestFilter {
  private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
      var optionalRefreshToken = getRefreshTokenFromCookies(request.getCookies());

      if (optionalRefreshToken.isEmpty()) {
        filterChain.doFilter(request, response);
        return;
      }

      
  }

  private Optional<String> getRefreshTokenFromCookies(Cookie[] cookies) {
    return Stream.of(cookies)
      .filter(this::isRefreshTokenCookie)
      .findFirst()
      .map(Cookie::getValue);
  }

  private boolean isRefreshTokenCookie(Cookie cookie) {
    return REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName());
  }
}
