package pro.shapeit.backend.training.plan.unit.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.backend.catalog.exercise.CatalogExercise;
import pro.shapeit.backend.common.exception.resource.ResourceNotFoundException;
import pro.shapeit.backend.training.plan.unit.TrainingUnit;
import pro.shapeit.backend.training.plan.unit.TrainingUnitRepository;

import static pro.shapeit.backend.common.util.ServiceUtils.updateIfNotNull;

@Service
@RequiredArgsConstructor
public class TrainingExerciseService {
  private final TrainingExerciseRepository trainingExerciseRepository;
  private final TrainingUnitRepository trainingUnitRepository;

  // --- Find methods ---

  public TrainingExercise findTrainingExerciseByLocalId(String localId) throws ResourceNotFoundException {
    return trainingExerciseRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Training exercise does not exist"));
  }

  // --- Save methods ---

  public TrainingExercise saveTrainingExercise(TrainingUnit unit, CatalogExercise catalogExercise) {
    var exercise = new TrainingExercise();
    exercise.setCatalogExercise(catalogExercise);
    var savedExercise = trainingExerciseRepository.save(exercise);
    unit.getExercises().add(savedExercise);
    trainingUnitRepository.save(unit);
    return savedExercise;
  }

  // --- Update methods ---

  public TrainingExercise updateTrainingExercise(
      TrainingExercise exercise,
      CatalogExercise catalogExercise,
      UpdateTrainingExerciseDto dto
  ) {
    updateIfNotNull(catalogExercise, exercise::setCatalogExercise);
    updateIfNotNull(dto.notes(), exercise::setNotes);

    return trainingExerciseRepository.save(exercise);
  }

  // --- Delete methods ---

  public boolean deleteTrainingExerciseByLocalId(String localId) {
    return trainingExerciseRepository.deleteByLocalIdWithCount(localId) > 0;
  }
}
