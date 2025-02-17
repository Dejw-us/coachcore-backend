package pro.shapeit.training.plan;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.transaction.annotation.Transactional;

import pro.coachcore.common.test.security.jwt.JwtTestContext;
import pro.shapeit.training.TestDtos;
import pro.shapeit.training.jwt.JwtTestConfig;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
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

  private String createdPlanId;

  @BeforeEach
  void setup() throws Exception {
    if (createdPlanId == null) {
      var requestBody = objectMapper.writeValueAsString(TestDtos.CREATE_PLAN_DTO);

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
  void shouldNotPostTrainingPlan() throws Exception {
    var requestBody = objectMapper.writeValueAsString(TestDtos.CREATE_PLAN_DTO);

    mockMvc.perform(post("/v1/training-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().is(401));
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
        .andExpect(jsonPath("$.goals.size()", is(TestDtos.CREATE_PLAN_DTO.goals().size())));
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

  @Test
  void shouldDeleteTrainingPlan() throws Exception {
    mockMvc.perform(delete("/v1/training-plans/" + createdPlanId)
            .with(jwtTestContext.getJwtPostProcessor(1)))
        .andExpect(status().isOk());
    createdPlanId = null;
  }

  @Test
  void shouldNotDeleteTrainingPlan() throws Exception {
    mockMvc.perform(delete("/v1/training-plans/" + createdPlanId)
            .with(jwtTestContext.getJwtPostProcessor(0)))
        .andExpect(status().is(403));
  }
}