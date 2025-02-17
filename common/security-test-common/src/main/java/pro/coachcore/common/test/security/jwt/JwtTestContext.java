package pro.coachcore.common.test.security.jwt;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class JwtTestContext {
  private final List<JwtTestToken> tokens = new ArrayList<>();

  public static JwtTestContext create(int tokensAmount) {
    return create(tokensAmount, (token, index) -> {
    });
  }

  public static JwtTestContext create(int tokensAmount, BiConsumer<JwtTestToken, Integer> tokenModifier) {
    var context = new JwtTestContext();
    for (int i = 0; i < tokensAmount; i++) {
      var token = new JwtTestToken();
      tokenModifier.accept(token, i);
      context.tokens.add(token);
    }
    return context;
  }

  public SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor getJwtPostProcessor(int index) {
    return SecurityMockMvcRequestPostProcessors.jwt().jwt(jwt -> {
      jwt.claim("id", getId(index));
      for (var entry : tokens.get(index).getClaims().entrySet()) {
        jwt.claim(entry.getKey(), entry.getValue());
      }
    });
  }

  public String getId(int index) {
    return tokens.get(index).getId();
  }

  @SuppressWarnings("unchecked")
  public <T> T getClaim(int index, String claim) {
    return (T) tokens.get(index).getClaims().get(claim);
  }
}
