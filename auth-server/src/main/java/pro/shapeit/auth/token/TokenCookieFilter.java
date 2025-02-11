package pro.shapeit.auth.token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
public class TokenCookieFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    log.info("Logging attributes");
    var attributes = request.getAttributeNames();
    var attribute = attributes.nextElement();
    while (attributes.hasMoreElements()) {
      log.info("Attribute: {}", attribute);
      attribute = attributes.nextElement();
    }
    filterChain.doFilter(request, response);
  }
}
