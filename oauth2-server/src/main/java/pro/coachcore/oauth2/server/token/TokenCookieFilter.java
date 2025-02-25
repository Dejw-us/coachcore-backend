package pro.coachcore.oauth2.server.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.oauth2.common.CookieTokensNames;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.*;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TokenCookieFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    if (!request.getRequestURI().toString().startsWith("/oauth2/token")) {
      filterChain.doFilter(request, response);
      return;
    }

    var responseWrapper = new ContentCachingResponseWrapper(response);

    filterChain.doFilter(request, responseWrapper);

    try {
      var body = new String(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());
      var tokensData = new ObjectMapper().readValue(body, TokensData.class);

      if (tokensData != null) {
        tokensData.addCookies(response);
      }
    } catch (JsonProcessingException ignore) {
      log.info("Failed to read tokens data. Ensure that reponse format is correct");
    }

    responseWrapper.copyBodyToResponse();
  }

  private record TokensData(
      String access_token,
      String refresh_token,
      String token_type,
      String expires_in,
      String id_token,
      String scope) {
    void addCookies(HttpServletResponse response) {
      addCookie(CookieTokensNames.ACCESS_TOKEN, access_token, 180, response);
      addCookie(CookieTokensNames.REFRESH_TOKEN, refresh_token, 360, response);
    }

    void addCookie(String name, @Nullable String value, int maxAge, HttpServletResponse response) {
      if (value == null) {
        return;
      }
      var cookie = new Cookie(name, value);
      cookie.setHttpOnly(true);
      cookie.setPath("/");
      cookie.setMaxAge(maxAge);
      response.addCookie(cookie);
      log.info("Added cookie with name {}", name);
    }
  }
}
