package pro.shapeit.training.plan.unit.exercise.set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.shapeit.exception.ResourceNotFoundException;
import pro.shapeit.training.plan.unit.exercise.TrainingExerciseRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingSetServiceTests {
  @Mock
  private TrainingExerciseRepository trainingExerciseRepository;

  @Mock
  private TrainingSetRepository trainingSetRepository;

  @InjectMocks
  private TrainingSetService trainingSetService;

  @Test
  void throwsResourceNotFound_deleteTrainingSetByLocalId() {
    var setId = UUID.randomUUID().toString();

    when(trainingSetRepository.existsByLocalId(setId)).thenReturn(false);

    assertThrows(
        ResourceNotFoundException.class,
        () -> trainingSetService.deleteTrainingSetByLocalId(setId)
    );
  }
}