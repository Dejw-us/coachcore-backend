package pro.shapeit.training.plan.unit.exercise.set;

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
import pro.shapeit.common.test.security.jwt.JwtTestContext;
import pro.shapeit.training.TestDtos;
import pro.shapeit.training.catalog.exercise.CatalogExerciseDto;
import pro.shapeit.training.jwt.JwtTestConfig;
import pro.shapeit.training.plan.TrainingPlanDto;
import pro.shapeit.training.plan.unit.TrainingUnitDto;
import pro.shapeit.training.plan.unit.exercise.TrainingExerciseDto;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Import(JwtTestConfig.class)
class TrainingSetControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private JwtTestContext jwtTestContext;

  private String createdPlanId;
  private String createdUnitId;
  private String createdExerciseId;
  private String createdSetId;

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

      result = mockMvc.perform(post("/v1/training-plans/" + createdPlanId + "/units")
              .with(jwtTestContext.getJwtPostProcessor(1))
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(TestDtos.CREATE_UNIT_DTO)))
          .andExpect(status().isCreated())
          .andReturn();

      createdUnitId = objectMapper.readValue(result.getResponse().getContentAsString(), TrainingUnitDto.class).id();

      result = mockMvc.perform(get("/v1/catalog-exercises"))
          .andReturn();

      var exercises = objectMapper.readValue(result.getResponse().getContentAsString(), CatalogExerciseDto[].class);
      var catalogExerciseId = exercises[0].id();

      result = mockMvc.perform(post("/v1/training-plans/{planId}/units/{unitId}/exercises", createdPlanId, createdUnitId)
              .with(jwtTestContext.getJwtPostProcessor(1))
              .param("catalogExerciseId", catalogExerciseId))
          .andExpect(status().isCreated())
          .andReturn();

      createdExerciseId = objectMapper.readValue(result.getResponse().getContentAsString(), TrainingExerciseDto.class).id();

      result = mockMvc.perform(post("/v1/training-plans/{planId}/exercises/{exerciseId}/sets", createdPlanId, createdExerciseId)
              .with(jwtTestContext.getJwtPostProcessor(1)))
          .andExpect(status().isCreated())
          .andReturn();

      createdSetId = objectMapper.readValue(result.getResponse().getContentAsString(), TrainingSetDto.class).id();
    }
  }

  @Test
  void shouldPatchTrainingSet() throws Exception {
    mockMvc.perform(patch("/v1/training-plans/{planId}/sets/{setId}", createdPlanId, createdSetId)
            .with(jwtTestContext.getJwtPostProcessor(1))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestDtos.UPDATE_SET_DTO)))
        .andExpect(jsonPath("$.reps", is(TestDtos.UPDATE_SET_DTO.reps())))
        .andExpect(jsonPath("$.intensity", is(TestDtos.UPDATE_SET_DTO.intensity())))
        .andExpect(jsonPath("$.intensityType", is(TestDtos.UPDATE_SET_DTO.intensityType())))
        .andExpect(jsonPath("$.rate", is(TestDtos.UPDATE_SET_DTO.rate())))
        .andExpect(jsonPath("$.restSeconds", is(TestDtos.UPDATE_SET_DTO.restSeconds())))
        .andExpect(jsonPath("$.weight", is(TestDtos.UPDATE_SET_DTO.weight())))
        .andExpect(jsonPath("$.weightType", is(TestDtos.UPDATE_SET_DTO.weightType())))
        .andExpect(status().isOk());
  }

  @Test
  void shouldDeleteTrainingSet() throws Exception {
    mockMvc.perform(delete("/v1/training-plans/{planId}/sets/{setId}", createdPlanId, createdSetId)
            .with(jwtTestContext.getJwtPostProcessor(1)))
        .andExpect(status().isOk());
  }
}