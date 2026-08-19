package pro.coachcore.training.plan.unit.exercise.set;

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
import pro.coachcore.training.plan.exercise.TrainingExerciseRepository;
import pro.coachcore.training.plan.set.TrainingSetRepository;
import pro.coachcore.training.plan.set.TrainingSetService;

@ExtendWith(MockitoExtension.class)
class TrainingSetServiceTests {
  @Mock
  private TrainingExerciseRepository trainingExerciseRepository;

  @Mock
  private TrainingSetRepository trainingSetRepository;

  @Mock
  private MessageService messageService;

  @InjectMocks
  private TrainingSetService trainingSetService;

  @Test
  void throwsResourceNotFound_deleteTrainingSetByLocalId() {
    var setId = UUID.randomUUID().toString();

    when(trainingSetRepository.existsByLocalId(setId)).thenReturn(false);

    assertThrows(ResourceNotFoundException.class, () -> trainingSetService.deleteSet(setId));
  }
}
