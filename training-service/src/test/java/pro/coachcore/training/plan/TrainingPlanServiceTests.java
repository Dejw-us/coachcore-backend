package pro.coachcore.training.plan;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;

@ExtendWith(MockitoExtension.class)
class TrainingPlanServiceTests {

  @Mock
  private TrainingPlanRepository trainingPlanRepository;

  @Mock
  private MessageService messageService;

  @InjectMocks
  private TrainingPlanService trainingPlanService;

  @Test
  void throwsResourceNotFound_deleteTrainingPlanByLocalId() {
    var planId = UUID.randomUUID().toString();

    when(trainingPlanRepository.existsByLocalId(planId)).thenReturn(false);

    assertThrows(ResourceNotFoundException.class, () -> trainingPlanService.deletePlan(planId));
  }
}
