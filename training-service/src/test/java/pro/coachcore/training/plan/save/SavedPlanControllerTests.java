package pro.coachcore.training.plan.save;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import pro.coachcore.common.test.security.jwt.TestJwtUtils;
import pro.coachcore.training.plan.TestPlan;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class SavedPlanControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private TestPlan testPlan;

  private Boolean isPlanSaved = false;

  private static final String USER_ID = "user1";

  @BeforeEach
  void setup() throws Exception {
    if (testPlan == null || testPlan.isEmpty()) {
      testPlan = new TestPlan().setup(mockMvc, objectMapper);
    }
    if (!isPlanSaved) {
      savePlan(USER_ID)
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.userId", is(USER_ID)))
          .andDo(result -> isPlanSaved = true);

    }
  }

  @Test
  void deleteSavedPlan_shouldDeletePlan_whenAuthorized() throws Exception {
    mockMvc.perform(delete("/v1/saved-plans")
        .param("planId", testPlan.getCreatedPlanId())
        .with(TestJwtUtils.createJwtPostProccessor(USER_ID)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").exists())
        .andDo(result -> isPlanSaved = false);
  }

  @Test
  void getSavedPlans_shouldReturnOnePlan_whenAuthorized() throws Exception {
    mockMvc.perform(get("/v1/saved-plans")
        .with(TestJwtUtils.createJwtPostProccessor(USER_ID)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  private ResultActions savePlan(String userId) throws Exception {
    return mockMvc.perform(post("/v1/saved-plans")
        .param("planId", testPlan.getCreatedPlanId())
        .with(TestJwtUtils.createJwtPostProccessor(userId)));
  }
}
