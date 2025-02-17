package pro.coachcore.auth.token;

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
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
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
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    var responseWrapper = new ContentCachingResponseWrapper(response);

    if ("true".equals(request.getHeader("Cookie-Refresh-Token"))) {
      filterChain.doFilter(new RequestWrapper(request), responseWrapper);
    } else {
      filterChain.doFilter(request, responseWrapper);
    }

    var body = new String(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

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

  @ToString
  private static class RequestWrapper extends HttpServletRequestWrapper {
    private final String refreshToken;
    private final String body;

    public RequestWrapper(HttpServletRequest request) {
      super(request);
      refreshToken = Arrays.stream(request.getCookies())
          .filter(cookie -> "refresh_token".equals(cookie.getName()))
          .findFirst()
          .map(Cookie::getValue)
          .orElseThrow();
      body = "grant_type=" + URLEncoder.encode("refresh_token", StandardCharsets.UTF_8)
          + "&refresh_token=" + URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);
      log.info("body set to: {}", body);
      log.info("request: {}", this);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
      return new BufferedServletInputStream(new ByteArrayInputStream(body.getBytes()));
    }

    @Override
    public BufferedReader getReader() throws IOException {
      return new BufferedReader(new InputStreamReader(getInputStream()));
    }
  }

  private static class BufferedServletInputStream extends ServletInputStream {
    private final ByteArrayInputStream bais;

    public BufferedServletInputStream(ByteArrayInputStream bais) {
      this.bais = bais;
    }

    @Override
    public boolean isFinished() {
      return false;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
    }

    @Override
    public int read() throws IOException {
      return bais.read();
    }
  }

  private record TokensData(
      String access_token,
      String refresh_token,
      String token_type,
      String expires_in,
      String id_token,
      String scope
  ) {
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
