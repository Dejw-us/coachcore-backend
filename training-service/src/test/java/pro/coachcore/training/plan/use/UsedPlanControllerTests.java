package pro.coachcore.training.plan.use;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pro.coachcore.common.test.security.jwt.TestJwtUtils;
import pro.coachcore.training.plan.TestPlan;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UsedPlanControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private TestPlan testPlan;

  @BeforeEach
  void setup() throws Exception {
    if (testPlan == null || testPlan.isEmpty()) {
      testPlan = new TestPlan().setup(mockMvc, objectMapper);
    }
  }

  @Test
  void testPostUsePlan() throws Exception {
    mockMvc.perform(post("/v1/used-plans")
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
        .param("planId", testPlan.getCreatedPlanId()))
        .andExpect(status().isCreated());
  }
}
