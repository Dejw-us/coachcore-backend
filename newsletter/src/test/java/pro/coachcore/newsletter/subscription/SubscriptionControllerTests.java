package pro.coachcore.newsletter.subscription;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest
public class SubscriptionControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private SubscriptionRepository subscriptionRepository;

  @Autowired
  private SubscriptionService subscriptionService;

  @BeforeEach
  void setupTest() {
    subscriptionRepository.deleteAll();
  }

  @Test
  void testGetSubscriptions_ShouldReturn2() throws Exception {
    subscriptionRepository.saveAll(List.of(
        new Subscription(null, "test@mail.no", "pl", 0L, null),
        new Subscription(null, "test@mail.fun", "en", 0L, null)));
    var jwt = Jwt.withTokenValue("test-token")
        .header("alg", "none")
        .claim("sub", "test-user")
        .claim("roles", List.of("ADMIN"))
        .build();
    var token = new JwtAuthenticationToken(jwt, List.of(new SimpleGrantedAuthority("ADMIN")));

    SecurityContextHolder.getContext().setAuthentication(token);

    mockMvc.perform(get("/newsletter/subscriptions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.size()", is(2)));
  }

  @Test
  void testSubscribe_shouldReturnOk() throws Exception {
    var dto = new CreateSubscriptionDto("test@mail.pl", "pl");

    mockMvc.perform(post("/newsletter/subscribe")
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk());
  }

  @Test
  @Transactional
  void testUnsubscribe_shouldReturnOk() throws Exception {
    var subscriptionDto = new Subscription(null, "test@mail.pl", "pl", 0L, "test");
    subscriptionRepository.save(subscriptionDto);

    mockMvc.perform(post("/newsletter/unsubscribe")
        .param("code", "test"))
        .andExpect(status().isOk());
  }
}
