package pro.coachcore.training.test;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
  @GetMapping("/roles")
  ResponseEntity<List<String>> testRoles(@AuthenticationPrincipal Jwt jwt) {
    return ResponseEntity.ok(jwt.getClaimAsStringList("roles"));
  }

  @GetMapping("/authorities")
  ResponseEntity<List<String>> testAuthorities() {
    return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
        .map(GrantedAuthority::getAuthority).toList());
  }
}
