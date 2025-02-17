package pro.shapeit.training.plan.unit;

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

import pro.coachcore.common.test.security.jwt.JwtTestContext;
import pro.shapeit.training.TestDtos;
import pro.shapeit.training.jwt.JwtTestConfig;
import pro.shapeit.training.plan.TrainingPlanDto;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Import(JwtTestConfig.class)
public class TrainingUnitControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private JwtTestContext jwtTestContext;

  private String createdPlanId;

  private String createdUnitId;

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
    }
  }

  @Test
  void shouldGetTrainingPlanUnits() throws Exception {
    mockMvc.perform(get("/v1/training-plans/" + createdPlanId + "/units")
            .with(jwtTestContext.getJwtPostProcessor(1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void shouldPatchTrainingPlanUnits() throws Exception {
    mockMvc.perform(patch("/v1/training-plans/" + createdPlanId + "/units/" + createdUnitId)
            .with(jwtTestContext.getJwtPostProcessor(1))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestDtos.UPDATE_UNIT_DTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.notes", is(TestDtos.UPDATE_UNIT_DTO.notes())))
        .andExpect(jsonPath("$.dayOfWeek", is(TestDtos.UPDATE_UNIT_DTO.dayOfWeek())))
        .andExpect(jsonPath("$.name", is(TestDtos.UPDATE_UNIT_DTO.name())));
  }

  @Test
  void shouldDeleteTrainingPlanUnit() throws Exception {
    mockMvc.perform(delete("/v1/training-plans/" + createdPlanId + "/units/" + createdUnitId)
            .with(jwtTestContext.getJwtPostProcessor(1)))
        .andExpect(status().isOk());
    createdUnitId = null;
  }
}
