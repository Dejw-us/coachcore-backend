package pro.coachcore.training.plan.set;

import static pro.coachcore.util.ServiceUtils.updateIfNotNull;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;
import pro.coachcore.training.plan.exercise.TrainingExercise;
import pro.coachcore.training.plan.exercise.TrainingExerciseRepository;

/**
 * Service class responsible for handling operations related to training sets within a training
 * exercise. Provides methods to retrieve, create, update, and delete training sets.
 */
@Service
@RequiredArgsConstructor
public class TrainingSetService {
  private final MessageService messageService;
  private final TrainingSetRepository trainingSetRepository;
  private final TrainingExerciseRepository trainingExerciseRepository;

  /**
   * Retrieves a specific training set by its local ID.
   *
   * @param localId the local ID of the training set.
   * @return the training set with the specified local ID.
   * @throws ResourceNotFoundException if the training set is not found.
   */
  public TrainingSet getSet(String localId) {
    return trainingSetRepository.findByLocalId(localId).orElseThrow(
        ResourceNotFoundException.supplier(messageService.getMessage("training.set.not-found")));
  }

  /**
   * Retrieves all training sets for a specific training exercise.
   *
   * @param exerciseLocalId the local ID of the training exercise.
   * @return a list of training sets for the specified exercise.
   * @throws ResourceNotFoundException if the training exercise is not found.
   */
  public List<TrainingSet> getExerciseSets(String exerciseLocalId) {
    if (!trainingExerciseRepository.existsByLocalId(exerciseLocalId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.exercise.not-found"));
    }
    return trainingSetRepository.findAllByTrainingExercise_LocalId(exerciseLocalId);
  }

  /**
   * Creates and saves a new training set for a given training exercise.
   *
   * @param exercise the training exercise for which the set will be created.
   * @return the saved training set.
   */
  public TrainingSet saveSet(TrainingExercise exercise) {
    var set = new TrainingSet();
    var index = trainingSetRepository.countByTrainingExercise_LocalId(exercise.getLocalId());
    set.setIndex(index + 1L);
    set.setTrainingExercise(exercise);
    var savedSet = trainingSetRepository.save(set);
    trainingExerciseRepository.save(exercise);
    return savedSet;
  }

  /**
   * Updates an existing training set with the provided data.
   *
   * @param set the training set to be updated.
   * @param dto the DTO containing the updated details for the training set.
   * @return the updated training set.
   */
  public TrainingSet updateSet(TrainingSet set, UpdateTrainingSetDto dto) {
    updateIfNotNull(dto.intensity(), set::setIntensity);
    updateIfNotNull(dto.rate(), set::setRate);
    updateIfNotNull(dto.reps(), set::setReps);
    updateIfNotNull(dto.restSeconds(), set::setRestSeconds);
    updateIfNotNull(dto.weight(), set::setWeight);

    return trainingSetRepository.save(set);
  }

  /**
   * Deletes a training set by its local ID.
   *
   * @param setLocalId the local ID of the training set to be deleted.
   * @return the local ID of the deleted set.
   * @throws ResourceNotFoundException if the training set is not found.
   */
  @Transactional
  public String deleteSet(String setLocalId) {
    if (!trainingSetRepository.existsByLocalId(setLocalId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.set.not-found"));
    }
    trainingSetRepository.deleteByLocalId(setLocalId);
    return setLocalId;
  }
}

