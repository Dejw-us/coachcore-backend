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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pro.shapeit.common.test.TokenRequestTestClient;
import pro.shapeit.training.plan.goal.TrainingGoalRepository;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Disabled
public class TrainingPlanControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private TrainingPlanRepository trainingPlanRepository;

  @Autowired
  private TrainingGoalRepository trainingGoalRepository;

  private String accessToken;

  @BeforeEach
  void setUp() {
    // Obtain access token
    TokenRequestTestClient tokenClient = new TokenRequestTestClient();
    accessToken = tokenClient.obtainAccessToken();

    // Clear database
    trainingGoalRepository.deleteAll();
    trainingPlanRepository.deleteAll();
  }

  @Test
  void shouldGetEmptyTrainingPlansList() throws Exception {
    mockMvc.perform(get("/v1/training-plans")
            .header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(0)));
  }

  @Test
  void shouldCreateAndRetrieveTrainingPlan() throws Exception {
    // Given
    CreateTrainingPlanDto dto = new CreateTrainingPlanDto("Plan A", "Description A", List.of("Goal1", "Goal2"));
    String requestBody = objectMapper.writeValueAsString(dto);

    // Create a training plan
    var result = mockMvc.perform(post("/v1/training-plans")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is("Plan A")))
        .andExpect(jsonPath("$.description", is("Description A")))
        .andReturn();

    // Extract plan ID
    String responseJson = result.getResponse().getContentAsString();
    TrainingPlanDto createdPlan = objectMapper.readValue(responseJson, TrainingPlanDto.class);
    String planId = createdPlan.id();

    // Retrieve the created plan
    mockMvc.perform(get("/v1/training-plans/" + planId)
            .header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Plan A")));
  }

  @Test
  void shouldUpdateTrainingPlan() throws Exception {
    // Given
    CreateTrainingPlanDto createDto = new CreateTrainingPlanDto("Plan B", "Description B", List.of("Goal1"));
    String createBody = objectMapper.writeValueAsString(createDto);

    var result = mockMvc.perform(post("/v1/training-plans")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(createBody))
        .andExpect(status().isCreated())
        .andReturn();

    String responseJson = result.getResponse().getContentAsString();
    TrainingPlanDto createdPlan = objectMapper.readValue(responseJson, TrainingPlanDto.class);
    String planId = createdPlan.id();

    // Update DTO
    UpdateTrainingPlanDto updateDto = new UpdateTrainingPlanDto("Updated Plan B", "Updated Desc");
    String updateBody = objectMapper.writeValueAsString(updateDto);

    // Perform PATCH request
    mockMvc.perform(patch("/v1/training-plans/" + planId)
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Updated Plan B")))
        .andExpect(jsonPath("$.description", is("Updated Desc")));
  }

  @Test
  void shouldDeleteTrainingPlan() throws Exception {
    // Given
    CreateTrainingPlanDto createDto = new CreateTrainingPlanDto("Plan C", "Description C", List.of("Goal1"));
    String createBody = objectMapper.writeValueAsString(createDto);

    var result = mockMvc.perform(post("/v1/training-plans")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(createBody))
        .andExpect(status().isCreated())
        .andReturn();

    String responseJson = result.getResponse().getContentAsString();
    TrainingPlanDto createdPlan = objectMapper.readValue(responseJson, TrainingPlanDto.class);
    String planId = createdPlan.id();

    // Perform DELETE request
    mockMvc.perform(delete("/v1/training-plans/" + planId)
            .header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", containsString("deleted")));
  }
}