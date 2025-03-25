package pro.coachcore.training.plan.unit.exercise.set;

import static org.hamcrest.core.Is.is;
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
import pro.coachcore.training.catalog.exercise.CatalogExerciseDto;
import pro.coachcore.training.plan.TrainingPlanDto;
import pro.coachcore.training.plan.exercise.TrainingExerciseDto;
import pro.coachcore.training.plan.set.TrainingSetDto;
import pro.coachcore.training.plan.unit.TrainingUnitDto;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class TrainingSetControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private String createdPlanId;
  private String createdUnitId;
  private String createdExerciseId;
  private String createdSetId;

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

      result = mockMvc.perform(get("/v1/catalog-exercises")).andReturn();

      var exercises = objectMapper.readValue(result.getResponse().getContentAsString(),
          CatalogExerciseDto[].class);
      var catalogExerciseId = exercises[0].id();

      result = mockMvc
          .perform(post("/v1/training-plans/{planId}/units/{unitId}/exercises", createdPlanId,
              createdUnitId).param("catalogExerciseId", catalogExerciseId))
          .andExpect(status().isCreated()).andReturn();

      createdExerciseId = objectMapper
          .readValue(result.getResponse().getContentAsString(), TrainingExerciseDto.class).id();

      result = mockMvc.perform(post("/v1/training-plans/{planId}/exercises/{exerciseId}/sets",
          createdPlanId, createdExerciseId)).andExpect(status().isCreated()).andReturn();

      createdSetId = objectMapper
          .readValue(result.getResponse().getContentAsString(), TrainingSetDto.class).id();
    }
  }

  @Test
  void shouldPatchTrainingSet() throws Exception {
    mockMvc
        .perform(patch("/v1/training-plans/{planId}/sets/{setId}", createdPlanId, createdSetId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestDtoFactory.updateSetDto())))
        .andExpect(jsonPath("$.reps", is(TestDtoFactory.updateSetDto().reps())))
        .andExpect(jsonPath("$.intensity", is(TestDtoFactory.updateSetDto().intensity())))
        .andExpect(jsonPath("$.rate", is(TestDtoFactory.updateSetDto().rate())))
        .andExpect(jsonPath("$.restSeconds", is(TestDtoFactory.updateSetDto().restSeconds())))
        .andExpect(jsonPath("$.weight", is(TestDtoFactory.updateSetDto().weight())))
        .andExpect(status().isOk());
  }

  @Test
  void shouldDeleteTrainingSet() throws Exception {
    mockMvc.perform(delete("/v1/training-plans/{planId}/sets/{setId}", createdPlanId, createdSetId))
        .andExpect(status().isOk());
  }
}
