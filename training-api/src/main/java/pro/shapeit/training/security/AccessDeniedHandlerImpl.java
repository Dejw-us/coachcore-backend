package pro.shapeit.training.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Map;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException
  ) throws IOException, ServletException {
    if (accessDeniedException.getCause() != null) {
      System.out.println("no null");
      System.out.println(accessDeniedException.getCause().getMessage());
    } else {
      System.out.println(accessDeniedException.getMessage());
    }
    try (var writer = response.getWriter()) {
      response.setContentType("application/json");
      response.setStatus(403);
      writer.println(new ObjectMapper().writeValueAsString(Map.of("message", "test")));
    }
  }
}
