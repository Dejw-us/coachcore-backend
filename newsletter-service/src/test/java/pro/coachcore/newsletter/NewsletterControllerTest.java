package pro.coachcore.newsletter;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pro.coachcore.newsletter.subscription.CreateSubscriptionDto;
import pro.coachcore.newsletter.subscription.SubscriptionRepository;
import pro.coachcore.newsletter.subscription.SubscriptionService;

/*
 * ! This integration test requires two services to run:
 * - email service
 * - auth server
 */
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class NewsletterControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SubscriptionService subscriptionService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private SubscriptionRepository subscriptionRepository;

  @BeforeEach
  void setupTest() {
    subscriptionRepository.deleteAll();
  }

  @Test
  void testSendNewsletter_ShouldSend1() throws Exception {
    var dto = new SendNewsletterDto("Test subject", "Test content", "pl");

    subscriptionService.subscribe(new CreateSubscriptionDto("test@mail.no", "pl"));

    mockMvc.perform(post("/newsletter/send")
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.emailsSent").value(1));
  }

  @Test
  void testSendNewsletter_ShouldNotSendAny() throws Exception {
    var dto = new SendNewsletterDto("Test subject", "Test content", "pl");

    mockMvc.perform(post("/newsletter/send")
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.emailsSent").value(0));
  }
}