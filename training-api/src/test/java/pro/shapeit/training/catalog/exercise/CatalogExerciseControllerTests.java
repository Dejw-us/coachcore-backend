package pro.shapeit.training.catalog.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import pro.shapeit.training.catalog.category.ExerciseCategoryDto;
import pro.shapeit.training.jwt.JwtTestConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Import(JwtTestConfig.class)
class CatalogExerciseControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private JwtTestContext jwtTestContext;

  @Test
  void shouldGetCatalogExercises() throws Exception {
    mockMvc.perform(get("/v1/catalog-exercises"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void shouldPostCatalogExercise() throws Exception {
    var result = mockMvc.perform(get("/v1/exercise-categories"))
        .andReturn();
    var json = result.getResponse().getContentAsString();
    var categoryId = objectMapper.readValue(json, ExerciseCategoryDto[].class)[0].id();

    mockMvc.perform(post("/v1/catalog-exercises")
            .param("categoryId", categoryId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestDtos.CREATE_CATALOG_EXERCISE_DTO)))
        .andExpect(status().isCreated());
  }
}