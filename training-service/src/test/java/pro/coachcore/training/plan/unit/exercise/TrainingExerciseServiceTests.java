package pro.coachcore.training.plan.unit.exercise;

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
import pro.coachcore.training.plan.exercise.TrainingExerciseService;
import pro.coachcore.training.plan.unit.TrainingUnitRepository;

@ExtendWith(MockitoExtension.class)
class TrainingExerciseServiceTests {
  @Mock
  private TrainingExerciseRepository trainingExerciseRepository;

  @Mock
  private TrainingUnitRepository trainingUnitRepository;

  @Mock
  private MessageService messageService;

  @InjectMocks
  private TrainingExerciseService trainingExerciseService;

  @Test
  void throwsResourceNotFound_deleteTrainingExerciseByLocalId() {
    var exerciseId = UUID.randomUUID().toString();

    when(trainingExerciseRepository.existsByLocalId(exerciseId)).thenReturn(false);

    assertThrows(
        ResourceNotFoundException.class,
        () -> trainingExerciseService.deleteTrainingExerciseByLocalId(exerciseId));
  }
}