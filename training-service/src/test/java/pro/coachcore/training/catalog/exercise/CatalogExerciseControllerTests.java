package pro.coachcore.training.catalog.exercise;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import pro.coachcore.training.TestConfig;
import pro.coachcore.training.TestDtoFactory;
import pro.coachcore.training.catalog.category.ExerciseCategoryDto;

@Transactional
@SpringBootTest(classes = TestConfig.class)
@Import(TestConfig.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = true)
class CatalogExerciseControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getCatalogExercise_shouldReturn200_forAnyUser() throws Exception {
    mockMvc.perform(get("/v1/catalog-exercises"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void postCatalogExercise_shoulGetCategoryAndReturn401_whenNoAdminRole() throws Exception {
    var result = mockMvc.perform(get("/v1/exercise-categories")).andReturn();
    var json = result.getResponse().getContentAsString();
    var categoryId = objectMapper.readValue(json, ExerciseCategoryDto[].class)[0].id();
    var requestBody = TestDtoFactory.createCatalogExerciseDto();

    mockMvc.perform(post("/v1/catalog-exercises")
        .param("categoryId", categoryId)
        .contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
        .andExpect(status().is(401));
  }

  @Test
  @WithMockUser(username = "user", authorities = "ADMIN")
  void postCatalogExercise_shoulGetCategoryAndReturn201_whenAdminAccess() throws Exception {
    var result = mockMvc.perform(get("/v1/exercise-categories")).andReturn();
    var json = result.getResponse().getContentAsString();
    var categoryId = objectMapper.readValue(json, ExerciseCategoryDto[].class)[0].id();
    var requestBody = TestDtoFactory.createCatalogExerciseDto();

    mockMvc.perform(post("/v1/catalog-exercises")
        .param("categoryId", categoryId)
        .contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)))
        .andExpect(status().is(201));
  }
}
