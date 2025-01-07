package pro.shapeit.api.training.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.common.id.LocalId;
import pro.shapeit.api.training.exercise.catalog.CatalogExercise;
import pro.shapeit.api.training.unit.TrainingUnit;

@Service
@RequiredArgsConstructor
public class TrainingExerciseService {
  private final TrainingExerciseRepository trainingExerciseRepository;
  private final TrainingExerciseMapper trainingExerciseMapper;

  public TrainingExercise saveTrainingExercise(
      TrainingUnit parent,
      CatalogExercise catalogExercise
  ) {
    var exercise = new TrainingExercise();

    exercise.setLocalId(LocalId.random());
    exercise.setTrainingUnit(parent);
    exercise.setCatalogExercise(catalogExercise);

    return trainingExerciseRepository.save(exercise);
  }
}
