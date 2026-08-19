package pro.coachcore.training.plan.use;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.AuditorAware;

@SpringBootTest // TODO add tests
public class UsedPlanServiceTests {
  @Mock
  private UsedPlanRepository usedPlanRepository;

  @Mock
  private AuditorAware<String> auditorAware;

  @InjectMocks
  private UsedPlanService usedPlanService;

  @Test
  void testGetPlans() {

  }

  @Test
  void testGetUsersAmount() {

  }

  @Test
  void testRemovePlan() {

  }
}
