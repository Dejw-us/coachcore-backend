package pro.coachcore.training.plan.unit;

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
  }

  @Test
  void getUnits_shouldGetUnits_whenPlanHasUnitsAndUserIsAuthorized() throws Exception {
    mockMvc.perform(get("/v1/training-plans/" + createdPlanId + "/units")
        .with(TestJwtUtils.createJwtPostProccessor("user1")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void patchUnit_shouldUpdateUnit_whenPlanHasUnitAndUserIsAuthorized() throws Exception {
    mockMvc.perform(patch("/v1/training-plans/" + createdPlanId + "/units/" + createdUnitId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(TestDtoFactory.updateUnitDto()))
        .with(TestJwtUtils.createJwtPostProccessor("user1")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.notes", is(TestDtoFactory.updateUnitDto().notes())))
        .andExpect(jsonPath("$.dayOfWeek", is(TestDtoFactory.updateUnitDto().dayOfWeek())))
        .andExpect(jsonPath("$.name", is(TestDtoFactory.updateUnitDto().name())));
  }

  @Test
  void deleteUnit_shouldDeleteUnit_whenUniteExistsAndUserIsAuthorized() throws Exception {
    mockMvc.perform(delete("/v1/training-plans/" + createdPlanId + "/units/" + createdUnitId)
        .with(TestJwtUtils.createJwtPostProccessor("user1")))
        .andExpect(status().isOk()).andDo(result -> createdUnitId = null);
  }
}
