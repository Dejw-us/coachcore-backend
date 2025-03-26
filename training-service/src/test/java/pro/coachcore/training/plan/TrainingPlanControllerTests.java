package pro.coachcore.training.plan;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import pro.coachcore.common.test.security.jwt.TestJwtUtils;
import pro.coachcore.training.TestDtoFactory;

@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TrainingPlanControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private String createdPlanId;

  @BeforeEach
  void setup() throws Exception {
    if (Objects.nonNull(createdPlanId)) {
      return;
    }

    var requestBody = objectMapper.writeValueAsString(TestDtoFactory.createPlanDto());

    mockMvc.perform(post("/v1/training-plans")
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isCreated())
        .andDo(result -> {
          var json = result.getResponse().getContentAsString();
          var plan = objectMapper.readValue(json, TrainingPlanDto.class);
          createdPlanId = plan.id();
        });
  }

  @Test
  void getUserPlans_shouldReturnUserPlans_forAuthenticatedUser() throws Exception {
    mockMvc.perform(get("/v1/training-plans/me")
        .with(TestJwtUtils.createJwtPostProccessor("user1")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void postPlan_shouldNotCreatePlan_whenNotAuthorized() throws Exception {
    var requestBody = objectMapper.writeValueAsString(TestDtoFactory.createPlanDto());
    mockMvc.perform(post("/v1/training-plans")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().is(401));
  }

  @Test
  void getPlans_shouldReturnPlans_forAnyAuth() throws Exception {
    mockMvc.perform(get("/v1/training-plans"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void getPlan_shouldReturnPlan_whenAuthorized() throws Exception {
    mockMvc.perform(get("/v1/training-plans/" + createdPlanId)
        .with(TestJwtUtils.createJwtPostProccessor("user1")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(createdPlanId)))
        .andExpect(jsonPath("$.createdBy", is("user1")))
        .andExpect(jsonPath("$.goals.size()", is(TestDtoFactory.createPlanDto().goals().size())));
  }

  @Test
  void patchPlan_shouldPatchPlan_whenAuthorized() throws Exception {
    var dto = TestDtoFactory.updatePlanDto();

    mockMvc.perform(patch("/v1/training-plans/" + createdPlanId)
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk()).andExpect(jsonPath("$.name", is(dto.name())))
        .andExpect(jsonPath("$.description", is(dto.description())));
  }

  @Test
  void deletePlan_shouldDeletePlan_whenAuthorized() throws Exception {
    mockMvc.perform(delete("/v1/training-plans/" + createdPlanId)
        .with(TestJwtUtils.createJwtPostProccessor("user1")))
        .andExpect(status().isOk())
        .andDo(result -> createdPlanId = null);
  }

  @Test
  void deletePlan_shouldNotDeletePlan_whenNotAuthorized() throws Exception {
    mockMvc.perform(delete("/v1/training-plans/" + createdPlanId)).andExpect(status().is(401));
  }
}
