package pro.coachcore.training.plan;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import pro.coachcore.common.test.security.jwt.TestJwtUtils;
import pro.coachcore.training.TestDtoFactory;
import pro.coachcore.training.catalog.exercise.CatalogExerciseDto;
import pro.coachcore.training.plan.exercise.TrainingExerciseDto;
import pro.coachcore.training.plan.set.TrainingSetDto;
import pro.coachcore.training.plan.unit.TrainingUnitDto;

@Getter
public class TestPlan {
  private String createdPlanId;
  private String createdUnitId;
  private String createdExerciseId;
  private String createdSetId;

  public boolean isEmpty() {
    return createdPlanId == null;
  }

  public void clear() {
    createdExerciseId = null;
    createdPlanId = null;
    createdSetId = null;
    createdUnitId = null;
  }

  public TestPlan setup(MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
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
    return this;
  }
}
