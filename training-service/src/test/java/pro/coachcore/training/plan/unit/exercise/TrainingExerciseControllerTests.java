package pro.coachcore.training.plan.unit.exercise;

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

import pro.coachcore.common.test.security.jwt.TestJwtUtils;
import pro.coachcore.training.TestDtoFactory;
import pro.coachcore.training.catalog.exercise.CatalogExerciseDto;
import pro.coachcore.training.plan.TrainingPlanDto;
import pro.coachcore.training.plan.exercise.TrainingExerciseDto;
import pro.coachcore.training.plan.unit.TrainingUnitDto;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class TrainingExerciseControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private String createdPlanId;
  private String createdUnitId;
  private String createdExerciseId;

  @BeforeEach
  void setup() throws Exception {
    if (Objects.nonNull(createdPlanId)) {
      return;
    }

    mockMvc.perform(post("/v1/training-plans")
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(TestDtoFactory.createPlanDto())))
        .andExpect(status().isCreated())
        .andDo(result -> {
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

    var response = mockMvc.perform(get("/v1/catalog-exercises"))
        .andReturn();

    var exercises = objectMapper.readValue(response.getResponse().getContentAsString(),
        CatalogExerciseDto[].class);
    var catalogExerciseId = exercises[0].id();

    mockMvc.perform(post("/v1/training-plans/{planId}/units/{unitId}/exercises", createdPlanId, createdUnitId)
        .param("catalogExerciseId", catalogExerciseId)
        .with(TestJwtUtils.createJwtPostProccessor("user1")))
        .andExpect(status().isCreated())
        .andDo(result -> {
          createdExerciseId = objectMapper
              .readValue(result.getResponse().getContentAsString(), TrainingExerciseDto.class).id();
        });
  }

  @Test
  void patchExercise_shouldUpdateExercise_whenPermittedAndExercsiseExists() throws Exception {
    mockMvc.perform(patch("/v1/training-plans/{planId}/exercises/{exerciseId}", createdPlanId, createdExerciseId)
        .contentType(MediaType.APPLICATION_JSON)
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
        .content(objectMapper.writeValueAsString(TestDtoFactory.updateExerciseDto())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.notes", is(TestDtoFactory.updateExerciseDto().notes())));
  }

  @Test
  void deleteExercise_shouldDeleteExercise_whenPermittedAndExerciseExists() throws Exception {
    mockMvc.perform(delete("/v1/training-plans/{planId}/exercises/{exerciseId}", createdPlanId, createdExerciseId)
        .with(TestJwtUtils.createJwtPostProccessor("user1")))
        .andExpect(status().isOk());
  }
}
