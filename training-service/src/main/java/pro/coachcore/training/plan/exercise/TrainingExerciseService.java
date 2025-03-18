package pro.coachcore.training.plan.exercise;

import static pro.coachcore.util.ServiceUtils.updateIfNotNull;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;
import pro.coachcore.training.catalog.exercise.CatalogExercise;
import pro.coachcore.training.plan.set.TrainingSetRepository;
import pro.coachcore.training.plan.unit.TrainingUnit;
import pro.coachcore.training.plan.unit.TrainingUnitRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingExerciseService {
  private final TrainingExerciseRepository trainingExerciseRepository;
  private final TrainingUnitRepository trainingUnitRepository;
  private final TrainingSetRepository setRepository;
  private final MessageService messageService;

  public List<TrainingExercise> findAllByUnitLocalId(String localId) {
    if (!trainingUnitRepository.existsByLocalId(localId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.unit.not-found"));
    }
    return trainingExerciseRepository.findAllByTrainingUnit_LocalId(localId);
  }

  public TrainingExercise findTrainingExerciseByLocalId(String localId) {
    return trainingExerciseRepository.findByLocalId(localId).orElseThrow(ResourceNotFoundException
        .supplier(messageService.getMessage("training.exercise.not-found")));
  }

  public TrainingExercise saveTrainingExercise(TrainingUnit unit, CatalogExercise catalogExercise) {
    var index = trainingExerciseRepository.countByTrainingUnit_LocalId(unit.getLocalId());
    var exercise = new TrainingExercise();
    exercise.setIndex(index + 1L);
    exercise.setCatalogExercise(catalogExercise);
    exercise.setTrainingUnit(unit);
    var savedExercise = trainingExerciseRepository.save(exercise);
    trainingUnitRepository.save(unit);
    return savedExercise;
  }

  public TrainingExercise updateTrainingExercise(TrainingExercise exercise,
      CatalogExercise catalogExercise, UpdateTrainingExerciseDto dto) {
    updateIfNotNull(catalogExercise, exercise::setCatalogExercise);
    updateIfNotNull(dto.notes(), exercise::setNotes);
    updateIfNotNull(dto.intensityType(), exercise::setIntensityType);
    updateIfNotNull(dto.weightType(), exercise::setWeightType);
    return trainingExerciseRepository.save(exercise);
  }

  @Transactional
  public String deleteTrainingExerciseByLocalId(String localId) {
    if (!trainingExerciseRepository.existsByLocalId(localId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.exercise.not-found"));
    }
    setRepository.deleteAllByTrainingExercise_LocalId(localId);
    trainingExerciseRepository.deleteByLocalId(localId);
    return localId;
  }
}
