package pro.coachcore.training.plan.exercise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static pro.coachcore.util.ServiceUtils.updateIfNotNull;

import java.util.List;

import org.springframework.stereotype.Service;

import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.training.catalog.exercise.CatalogExercise;
import pro.coachcore.training.plan.unit.TrainingUnit;
import pro.coachcore.training.plan.unit.TrainingUnitRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingExerciseService {
  private final TrainingExerciseRepository trainingExerciseRepository;
  private final TrainingUnitRepository trainingUnitRepository;

  public List<TrainingExercise> findAllByUnitLocalId(String localId) {
    if (!trainingUnitRepository.existsByLocalId(localId)) {
      throw new ResourceNotFoundException("Training unit does not exist");
    }
    return trainingExerciseRepository.findAllByTrainingUnit_LocalId(localId);
  }

  public TrainingExercise findTrainingExerciseByLocalId(String localId) {
    return trainingExerciseRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Training exercise does not exist"));
  }

  public TrainingExercise saveTrainingExercise(TrainingUnit unit, CatalogExercise catalogExercise) {
    var exercise = new TrainingExercise();
    exercise.setCatalogExercise(catalogExercise);
    exercise.setUnit(unit);
    var savedExercise = trainingExerciseRepository.save(exercise);
    trainingUnitRepository.save(unit);
    return savedExercise;
  }

  public TrainingExercise updateTrainingExercise(
      TrainingExercise exercise,
      CatalogExercise catalogExercise,
      UpdateTrainingExerciseDto dto) {
    log.debug("dto: {}", dto);
    updateIfNotNull(catalogExercise, exercise::setCatalogExercise);
    updateIfNotNull(dto.notes(), exercise::setNotes);
    log.debug("after update: {}", exercise);
    return trainingExerciseRepository.save(exercise);
  }

  public void deleteTrainingExerciseByLocalId(String localId) {
    if (!trainingExerciseRepository.existsByLocalId(localId)) {
      throw new ResourceNotFoundException("Training exercise does not exist");
    }
    trainingExerciseRepository.deleteByLocalId(localId);
  }
}
