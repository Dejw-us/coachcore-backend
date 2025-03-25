package pro.coachcore.jwt;

import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
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
      return null;
    }

    var claim = jwt.getClaimAsStringList("roles");

    if (claim == null) {
      return null;
    }

    var roles = claim.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    return new JwtAuthenticationToken(jwt, roles);
  }
}
