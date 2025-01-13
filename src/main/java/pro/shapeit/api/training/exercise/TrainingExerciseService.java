package pro.shapeit.api.training.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.common.id.LocalId;
import pro.shapeit.api.training.exercise.catalog.CatalogExercise;
import pro.shapeit.api.training.set.TrainingSet;
import pro.shapeit.api.training.set.TrainingSetRepository;
import pro.shapeit.api.training.unit.TrainingUnit;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingExerciseService {
  private final TrainingExerciseRepository trainingExerciseRepository;
  private final TrainingSetRepository trainingSetRepository;

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

  public List<TrainingSet> findTrainingExerciseSets(String localId) {
    return trainingSetRepository.findByTrainingExercise_LocalId(localId);
  }

  public TrainingExercise findTrainingExercise(String localId) throws ResourceNotFoundException {
    return trainingExerciseRepository.findByLocalId(localId)
        .orElseThrow(() -> new ResourceNotFoundException("Training exercise does not exist"));
  }
}
