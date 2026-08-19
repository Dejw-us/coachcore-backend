package pro.coachcore.training.plan.parameter;

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
import pro.coachcore.training.plan.unit.TrainingUnitDto;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ParameterDisplayControllerTests {
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
  void getParameterDisplay_shouldReturnDisplay_whenUnitExists() throws Exception {
    mockMvc.perform(get("/v1/training-plans/" + createdPlanId + "/units/" + createdUnitId + "/display")
        .with(TestJwtUtils.createJwtPostProccessor("user1")))
        .andExpect(status().isOk());
  }

  @Test
  void patchParameterDisplay_shouldUpdateDisplay_whenUnitExists() throws Exception {
    mockMvc.perform(patch("/v1/training-plans/" + createdPlanId + "/units/" + createdUnitId + "/display")
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(TestDtoFactory.updateDisplayDto())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.displayIntensity").value(true));
  }
}
