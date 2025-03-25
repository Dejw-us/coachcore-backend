package pro.coachcore.training.plan.unit;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import pro.coachcore.training.TestDtoFactory;
import pro.coachcore.training.plan.TrainingPlanDto;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TrainingUnitControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private String createdPlanId;

  private String createdUnitId;

  @BeforeEach
  void setup() throws Exception {
    if (createdPlanId == null) {
      var requestBody = objectMapper.writeValueAsString(TestDtoFactory.createPlanDto());

      var result = mockMvc.perform(
          post("/v1/training-plans").contentType(MediaType.APPLICATION_JSON).content(requestBody))
          .andExpect(status().isCreated()).andReturn();

      var json = result.getResponse().getContentAsString();
      var plan = objectMapper.readValue(json, TrainingPlanDto.class);
      createdPlanId = plan.id();

      result = mockMvc
          .perform(post("/v1/training-plans/" + createdPlanId + "/units")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(TestDtoFactory.createUnitDto())))
          .andExpect(status().isCreated()).andReturn();

      createdUnitId = objectMapper
          .readValue(result.getResponse().getContentAsString(), TrainingUnitDto.class).id();
    }
  }

  @Test
  void shouldGetTrainingPlanUnits() throws Exception {
    mockMvc.perform(get("/v1/training-plans/" + createdPlanId + "/units"))
        .andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
  }

  @Test
  void shouldPatchTrainingPlanUnits() throws Exception {
    mockMvc
        .perform(patch("/v1/training-plans/" + createdPlanId + "/units/" + createdUnitId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestDtoFactory.updateUnitDto())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.notes", is(TestDtoFactory.updateUnitDto().notes())))
        .andExpect(jsonPath("$.dayOfWeek", is(TestDtoFactory.updateUnitDto().dayOfWeek())))
        .andExpect(jsonPath("$.name", is(TestDtoFactory.updateUnitDto().name())));
  }

  @Test
  void shouldDeleteTrainingPlanUnit() throws Exception {
    mockMvc.perform(delete("/v1/training-plans/" + createdPlanId + "/units/" + createdUnitId))
        .andExpect(status().isOk()).andDo(result -> createdUnitId = null);
  }
}
