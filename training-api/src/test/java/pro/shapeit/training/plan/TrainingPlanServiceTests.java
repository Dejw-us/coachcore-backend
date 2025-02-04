package pro.shapeit.training.plan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.shapeit.exception.ResourceNotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingPlanServiceTests {

  @Mock
  private TrainingPlanRepository trainingPlanRepository;

  @InjectMocks
  private TrainingPlanService trainingPlanService;

  @Test
  void throwsResourceNotFound_deleteTrainingPlanByLocalId() {
    var planId = UUID.randomUUID().toString();

    when(trainingPlanRepository.existsByLocalId(planId)).thenReturn(false);

    assertThrows(
        ResourceNotFoundException.class,
        () -> trainingPlanService.deleteTrainingPlanByLocalId(planId)
    );
  }
}