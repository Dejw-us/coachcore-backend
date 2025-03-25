package pro.coachcore.training.plan.unit.exercise.set;

import static org.hamcrest.core.Is.is;
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

import pro.coachcore.common.test.security.jwt.TestJwtUtils;
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
    if (Objects.nonNull(createdPlanId)) {
      return;
    }

    mockMvc.perform(post("/v1/training-plans")
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(TestDtoFactory.createPlanDto())))
        .andExpect(status().isCreated()).andDo(result -> {
          var json = result.getResponse().getContentAsString();
          var plan = objectMapper.readValue(json, TrainingPlanDto.class);
          createdPlanId = plan.id();
        });

    mockMvc.perform(post("/v1/training-plans/" + createdPlanId + "/units")
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(TestDtoFactory.createUnitDto())))
        .andExpect(status().isCreated())
        .andDo(result -> {
          var json = result.getResponse().getContentAsString();
          var unit = objectMapper.readValue(json, TrainingUnitDto.class);
          createdUnitId = unit.id();
        });

    var response = mockMvc.perform(get("/v1/catalog-exercises")).andReturn();

    var exercises = objectMapper.readValue(response.getResponse().getContentAsString(),
        CatalogExerciseDto[].class);
    var catalogExerciseId = exercises[0].id();

    mockMvc.perform(post("/v1/training-plans/{planId}/units/{unitId}/exercises", createdPlanId, createdUnitId)
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
        .param("catalogExerciseId", catalogExerciseId))
        .andExpect(status().isCreated())
        .andDo(result -> {
          var json = result.getResponse().getContentAsString();
          var exercise = objectMapper.readValue(json, TrainingExerciseDto.class);
          createdExerciseId = exercise.id();
        });

    mockMvc.perform(post("/v1/training-plans/{planId}/exercises/{exerciseId}/sets", createdPlanId, createdExerciseId)
        .with(TestJwtUtils.createJwtPostProccessor("user1")))
        .andExpect(status().isCreated())
        .andDo(result -> {
          var json = result.getResponse().getContentAsString();
          var set = objectMapper.readValue(json, TrainingSetDto.class);
          createdSetId = set.id();
        });
  }

  @Test
  void patchSet_shouldUpdateSet_whenSetExistsAndPermitted() throws Exception {
    mockMvc.perform(patch("/v1/training-plans/{planId}/sets/{setId}", createdPlanId, createdSetId)
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
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
  void deleteSet_shouldDeleteSet_whenPermittedAndSetExists() throws Exception {
    mockMvc.perform(delete("/v1/training-plans/{planId}/sets/{setId}", createdPlanId, createdSetId)
        .with(TestJwtUtils.createJwtPostProccessor("user1")))
        .andExpect(status().isOk());
  }
}
