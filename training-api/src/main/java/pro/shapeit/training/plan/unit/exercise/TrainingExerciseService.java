package pro.shapeit.training.plan.unit.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pro.shapeit.exception.ResourceNotFoundException;
import pro.shapeit.training.catalog.exercise.CatalogExercise;
import pro.shapeit.training.plan.unit.TrainingUnit;
import pro.shapeit.training.plan.unit.TrainingUnitRepository;

import static pro.shapeit.util.ServiceUtils.updateIfNotNull;

@Service
@RequiredArgsConstructor
public class TrainingExerciseService {
  private final TrainingExerciseRepository trainingExerciseRepository;
  private final TrainingUnitRepository trainingUnitRepository;

  public TrainingExercise findTrainingExerciseByLocalId(String localId) throws ResourceNotFoundException {
    return trainingExerciseRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Training exercise does not exist"));
  }

  public TrainingExercise saveTrainingExercise(TrainingUnit unit, CatalogExercise catalogExercise) {
    var exercise = new TrainingExercise();
    exercise.setCatalogExercise(catalogExercise);
    var savedExercise = trainingExerciseRepository.save(exercise);
    unit.getExercises().add(savedExercise);
    trainingUnitRepository.save(unit);
    return savedExercise;
  }

  public TrainingExercise updateTrainingExercise(
      TrainingExercise exercise,
      CatalogExercise catalogExercise,
      UpdateTrainingExerciseDto dto
  ) {
    updateIfNotNull(catalogExercise, exercise::setCatalogExercise);
    updateIfNotNull(dto.notes(), exercise::setNotes);

    return trainingExerciseRepository.save(exercise);
  }

  public void deleteTrainingExerciseByLocalId(String localId) throws ResourceNotFoundException {
    if (!trainingExerciseRepository.existsByLocalId(localId)) {
      throw new ResourceNotFoundException("Training exercise does not exist");
    }
    trainingExerciseRepository.deleteByLocalId(localId);
  }
}
