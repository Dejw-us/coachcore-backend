package pro.coachcore.training.plan.rating;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import pro.coachcore.common.test.security.jwt.TestJwtUtils;
import pro.coachcore.training.TestDtoFactory;
import pro.coachcore.training.plan.TrainingPlanDto;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TrainingPlanRatingControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private String createdPlanId;

  @BeforeEach
  void setup() throws Exception {
    if (Objects.nonNull(createdPlanId)) {
      return;
    }

    var requestBody = objectMapper.writeValueAsString(TestDtoFactory.createPlanDto());

    mockMvc.perform(post("/v1/training-plans")
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isCreated())
        .andDo(result -> {
          var json = result.getResponse().getContentAsString();
          var plan = objectMapper.readValue(json, TrainingPlanDto.class);
          createdPlanId = plan.id();
        });
  }

  @Test
  void putRating_shouldUpdateRating_whenAuthorized() throws Exception {
    mockMvc.perform(put("/v1/rating/{planId}", createdPlanId)
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new UpdateTrainingPlanRatingDto(5))))
        .andExpect(status().isOk());
  }

  @Test
  void getAverageRating_shouldReturnAverageRating_forAnyUser() throws Exception {
    mockMvc.perform(put("/v1/rating/{planId}", createdPlanId)
        .with(TestJwtUtils.createJwtPostProccessor("user1"))
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new UpdateTrainingPlanRatingDto(2))))
        .andExpect(status().isOk());
    mockMvc.perform(put("/v1/rating/{planId}", createdPlanId)
        .with(TestJwtUtils.createJwtPostProccessor("user2"))
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new UpdateTrainingPlanRatingDto(4))))
        .andExpect(status().isOk());
    mockMvc.perform(get("/v1/rating/{planId}/average", createdPlanId))
        .andExpect(jsonPath("$.stars", is(3)))
        .andExpect(status().isOk());
  }
}
