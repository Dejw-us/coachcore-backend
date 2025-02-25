package pro.coachcore.oauth2.server.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
public class TokenCookieFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    log.info(request.getRequestURI().toString());
    if (request.getRequestURI().toString().startsWith("/oauth2/token")) {
      for (var entry : request.getParameterMap().entrySet()) {
        log.info("{}: {}", entry.getKey(), entry.getValue());
      }
      filterChain.doFilter(request, response);
      return;
    }
    var responseWrapper = new ContentCachingResponseWrapper(response);
    var body = new String(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

    filterChain.doFilter(request, responseWrapper);

    try {
      var tokensData = new ObjectMapper().readValue(body, TokensData.class);

      if (tokensData != null) {
        tokensData.addCookies(response);
      }
    } catch (JsonProcessingException exception) {
      log.warn("Failed to read json tokens data: {}", exception.getMessage());
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
      addCookie("access_token", access_token, 180, response);
      addCookie("refresh_token", refresh_token, 360, response);
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
    }
  }
}
