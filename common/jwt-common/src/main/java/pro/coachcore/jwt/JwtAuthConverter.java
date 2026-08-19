package pro.coachcore.jwt;

import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
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
      throw new AuthenticationCredentialsNotFoundException("Jwt token not found");
    }

    var claim = jwt.getClaimAsStringList("roles");

    if (claim == null) {
      return new JwtAuthenticationToken(jwt);
    }

    var roles = claim.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    return new JwtAuthenticationToken(jwt, roles);
  }
}
