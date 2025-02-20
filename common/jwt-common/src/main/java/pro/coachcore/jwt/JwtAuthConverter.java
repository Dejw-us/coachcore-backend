package pro.coachcore.jwt;

import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthConverter implements Converter<Jwt, JwtAuthenticationToken> {
  @Override
  @Nullable
  public JwtAuthenticationToken convert(@Nullable Jwt jwt) {
    if (jwt == null) {
      log.info("Jwt == null");
      return null;
    }
    var roles = jwt.getClaimAsStringList("roles").stream()
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());

    for (var role : roles) {
      log.info("Role: {}", role.getAuthority());
    }

    return new JwtAuthenticationToken(jwt, roles);
  }
}
