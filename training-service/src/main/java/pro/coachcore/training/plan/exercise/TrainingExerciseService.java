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

/**
 * Service class responsible for managing training exercises associated with training units.
 * Provides methods to retrieve, create, update, and delete training exercises.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingExerciseService {
  private final TrainingExerciseRepository trainingExerciseRepository;
  private final TrainingUnitRepository trainingUnitRepository;
  private final TrainingSetRepository setRepository;
  private final MessageService messageService;

  /**
   * Retrieves all training exercises for a specific training unit identified by its local ID.
   *
   * @param localId the local ID of the training unit.
   * @return a list of TrainingExercise objects for the specified unit.
   * @throws ResourceNotFoundException if the training unit does not exist.
   */
  public List<TrainingExercise> findAllByUnitLocalId(String localId) {
    if (!trainingUnitRepository.existsByLocalId(localId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.unit.not-found"));
    }
    return trainingExerciseRepository.findAllByTrainingUnit_LocalId(localId);
  }

  /**
   * Retrieves a specific training exercise identified by its local ID.
   *
   * @param localId the local ID of the training exercise.
   * @return the TrainingExercise object with the specified local ID.
   * @throws ResourceNotFoundException if the training exercise does not exist.
   */
  public TrainingExercise findTrainingExerciseByLocalId(String localId) {
    return trainingExerciseRepository.findByLocalId(localId).orElseThrow(ResourceNotFoundException
        .supplier(messageService.getMessage("training.exercise.not-found")));
  }

  /**
   * Creates and saves a new training exercise for a given training unit and catalog exercise.
   *
   * @param unit the TrainingUnit object that the new exercise will belong to.
   * @param catalogExercise the CatalogExercise object that defines the exercise.
   * @return the saved TrainingExercise object.
   */
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

  /**
   * Updates an existing training exercise with new values.
   *
   * @param exercise the existing TrainingExercise object to be updated.
   * @param catalogExercise the new CatalogExercise object to set.
   * @param dto the DTO containing updated information for the exercise.
   * @return the updated TrainingExercise object.
   */
  public TrainingExercise updateTrainingExercise(TrainingExercise exercise,
      CatalogExercise catalogExercise, UpdateTrainingExerciseDto dto) {
    updateIfNotNull(catalogExercise, exercise::setCatalogExercise);
    updateIfNotNull(dto.notes(), exercise::setNotes);
    updateIfNotNull(dto.intensityType(), exercise::setIntensityType);
    updateIfNotNull(dto.weightType(), exercise::setWeightType);
    return trainingExerciseRepository.save(exercise);
  }

  /**
   * Deletes a training exercise by its local ID, including all associated training sets.
   *
   * @param localId the local ID of the training exercise to be deleted.
   * @return the local ID of the deleted training exercise.
   * @throws ResourceNotFoundException if the training exercise does not exist.
   */
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

