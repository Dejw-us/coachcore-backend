package pro.coachcore.newsletter.subscription;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.exception.ResourceNotFoundException;

@AutoConfigureTestDatabase
@SpringBootTest
public class SubscriptionServiceTests {
  @Autowired
  private SubscriptionService subscriptionService;

  @Autowired
  private SubscriptionRepository subscriptionRepository;

  @BeforeEach
  void setupTest() {
    subscriptionRepository.deleteAll();
  }

  @Test
  void testSubscribe_shouldThrowException() {
    var dto = new CreateSubscriptionDto("test@mail.com", "pl");
    subscriptionService.subscribe(dto);
    assertThrows(
        ResourceAlreadyExistsException.class, () -> {
          subscriptionService.subscribe(dto);
        });
  }

  @Test
  void testUnsubscribe_shouldThrowException() {
    assertThrows(
      ResourceNotFoundException.class, () -> {
        subscriptionService.unsubscribe("code");
      });
  }
}
