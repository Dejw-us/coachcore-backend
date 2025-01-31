package pro.shapeit.common.test.security.jwt;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class JwtTestToken {
  private final String id;
  private final Map<String, Object> claims = new HashMap<>();

  public JwtTestToken() {
    this.id = UUID.randomUUID().toString();
  }

  public void setClaim(String claim, Object value) {
    claims.put(claim, value);
  }
}
