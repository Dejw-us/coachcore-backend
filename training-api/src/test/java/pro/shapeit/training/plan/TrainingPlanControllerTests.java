package pro.shapeit.training.plan;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pro.shapeit.common.test.security.jwt.JwtTestContext;
import pro.shapeit.training.jwt.JwtTestConfig;
import pro.shapeit.training.plan.goal.TrainingGoalRepository;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Import(JwtTestConfig.class)
public class TrainingPlanControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private JwtTestContext jwtTestContext;

  @Autowired
  private TrainingPlanRepository trainingPlanRepository;

  @Autowired
  private TrainingGoalRepository trainingGoalRepository;

  private String createdPlanId;

  private static final CreateTrainingPlanDto CREATE_PLAN_DTO = new CreateTrainingPlanDto(
      "Plan A",
      "Description A",
      List.of("Goal1", "Goal2")
  );

  @BeforeEach
  void setup() throws Exception {
    if (createdPlanId == null) {
      var requestBody = objectMapper.writeValueAsString(CREATE_PLAN_DTO);

      var result = mockMvc.perform(post("/v1/training-plans")
              .with(jwtTestContext.getJwtPostProcessor(1))
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody))
          .andExpect(status().isCreated())
          .andReturn();

      var json = result.getResponse().getContentAsString();
      var plan = objectMapper.readValue(json, TrainingPlanDto.class);
      createdPlanId = plan.id();
    }
  }

  @Test
  void shouldGetTrainingPlans() throws Exception {
    mockMvc.perform(get("/v1/training-plans"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void shouldGetTrainingPlan() throws Exception {
    mockMvc.perform(get("/v1/training-plans/" + createdPlanId)
            .with(jwtTestContext.getJwtPostProcessor(1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(createdPlanId)))
        .andExpect(jsonPath("$.createdBy", is(jwtTestContext.getId(1))))
        .andExpect(jsonPath("$.goals.size()", is(CREATE_PLAN_DTO.goals().size())));
  }

  @Test
  void shouldPatchTrainingPlan() throws Exception {
    var dto = new UpdateTrainingPlanDto("New name", "new description");

    mockMvc.perform(patch("/v1/training-plans/" + createdPlanId)
            .with(jwtTestContext.getJwtPostProcessor(1))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(dto.name())))
        .andExpect(jsonPath("$.description", is(dto.description())));
  }
}