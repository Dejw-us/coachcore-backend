package pro.shapeit.training.plan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.shapeit.exception.ResourceNotFoundException;
import pro.shapeit.training.plan.goal.TrainingGoal;
import pro.shapeit.training.plan.goal.TrainingGoalRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingPlanServiceTests {
  @Mock
  private TrainingPlanRepository trainingPlanRepository;

  @Mock
  private TrainingGoalRepository trainingGoalRepository;

  @InjectMocks
  private TrainingPlanService trainingPlanService;

  private TrainingPlan trainingPlan;

  @BeforeEach
  void setUp() {
    trainingPlan = new TrainingPlan();
    trainingPlan.setName("Test Plan");
    trainingPlan.setDescription("Test Description");
    trainingPlan.setGoals(List.of(new TrainingGoal("goal1"), new TrainingGoal("goal2")));
  }

  @Test
  void shouldFindAllTrainingPlans() {
    // Given
    when(trainingPlanRepository.findAll()).thenReturn(List.of(trainingPlan));

    // When
    List<TrainingPlan> result = trainingPlanService.findAllTrainingPlans();

    // Then
    assertEquals(1, result.size());
    assertEquals("Test Plan", result.get(0).getName());
    verify(trainingPlanRepository, times(1)).findAll();
  }

  @Test
  void shouldFindTrainingPlanByLocalId() throws ResourceNotFoundException {
    // Given
    when(trainingPlanRepository.findByLocalId("plan123")).thenReturn(Optional.of(trainingPlan));

    // When
    TrainingPlan result = trainingPlanService.findTrainingPlanByLocalId("plan123");

    // Then
    assertNotNull(result);
    assertEquals("Test Plan", result.getName());
    verify(trainingPlanRepository, times(1)).findByLocalId("plan123");
  }

  @Test
  void shouldThrowExceptionWhenTrainingPlanNotFound() {
    // Given
    when(trainingPlanRepository.findByLocalId("invalidId")).thenReturn(Optional.empty());

    // When & Then
    assertThrows(ResourceNotFoundException.class, () -> trainingPlanService.findTrainingPlanByLocalId("invalidId"));
  }

  @Test
  void shouldSaveTrainingPlan() {
    // Given
    var dto = new CreateTrainingPlanDto("New Plan", "New Desc", List.of("goal1", "goal2"));
    var goals = dto.goals().stream().map(TrainingGoal::new).toList();
    when(trainingGoalRepository.saveAll(anyList())).thenReturn(goals);
    when(trainingPlanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    // When
    TrainingPlan savedPlan = trainingPlanService.saveTrainingPlan(dto);

    // Then
    assertNotNull(savedPlan);
    assertEquals("New Plan", savedPlan.getName());
    assertEquals(2, savedPlan.getGoals().size());
    verify(trainingGoalRepository, times(1)).saveAll(anyList());
    verify(trainingPlanRepository, times(1)).save(any());
  }

  @Test
  void shouldUpdateTrainingPlan() {
    // Given
    var dto = new UpdateTrainingPlanDto("Updated Name", null);
    when(trainingPlanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    // When
    TrainingPlan updatedPlan = trainingPlanService.updateTrainingPlan(trainingPlan, dto);

    // Then
    assertEquals("Updated Name", updatedPlan.getName());
    assertEquals("Test Description", updatedPlan.getDescription()); // Unchanged
    verify(trainingPlanRepository, times(1)).save(trainingPlan);
  }

  @Test
  void shouldDeleteTrainingPlanByLocalId() {
    // Given
    when(trainingPlanRepository.deleteByLocalIdWithCount("plan123")).thenReturn(1);

    // When
    boolean result = trainingPlanService.deleteTrainingPlanByLocalId("plan123");

    // Then
    assertTrue(result);
    verify(trainingPlanRepository, times(1)).deleteByLocalIdWithCount("plan123");
  }

  @Test
  void shouldReturnFalseIfTrainingPlanNotDeleted() {
    // Given
    when(trainingPlanRepository.deleteByLocalIdWithCount("plan123")).thenReturn(0);

    // When
    boolean result = trainingPlanService.deleteTrainingPlanByLocalId("plan123");

    // Then
    assertFalse(result);
  }

  @Test
  void shouldReturnTrueIfUserIsOwner() {
    // Given
    when(trainingPlanRepository.existsByLocalIdAndCreatedBy("plan123", "user1")).thenReturn(true);

    // When
    boolean isOwner = trainingPlanService.isTrainingPlanOwner("user1", "plan123");

    // Then
    assertTrue(isOwner);
  }

  @Test
  void shouldReturnFalseIfUserIsNotOwner() {
    // Given
    when(trainingPlanRepository.existsByLocalIdAndCreatedBy("plan123", "user1")).thenReturn(false);

    // When
    boolean isOwner = trainingPlanService.isTrainingPlanOwner("user1", "plan123");

    // Then
    assertFalse(isOwner);
  }
}
