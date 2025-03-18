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

@Service
@RequiredArgsConstructor
public class TrainingSetService {
  private final MessageService messageService;
  private final TrainingSetRepository trainingSetRepository;
  private final TrainingExerciseRepository trainingExerciseRepository;

  public TrainingSet getSet(String localId) {
    return trainingSetRepository.findByLocalId(localId).orElseThrow(
        ResourceNotFoundException.supplier(messageService.getMessage("training.set.not-found")));
  }

  public List<TrainingSet> getExerciseSets(String exerciseLocalId) {
    if (!trainingExerciseRepository.existsByLocalId(exerciseLocalId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.exercise.not-found"));
    }
    return trainingSetRepository.findAllByTrainingExercise_LocalId(exerciseLocalId);
  }

  public TrainingSet saveSet(TrainingExercise exercise) {
    var set = new TrainingSet();
    var index = trainingSetRepository.countByTrainingExercise_LocalId(exercise.getLocalId());
    set.setIndex(index + 1L);
    set.setTrainingExercise(exercise);
    var savedSet = trainingSetRepository.save(set);
    trainingExerciseRepository.save(exercise);
    return savedSet;
  }

  public TrainingSet updateSet(TrainingSet set, UpdateTrainingSetDto dto) {
    updateIfNotNull(dto.intensity(), set::setIntensity);
    updateIfNotNull(dto.rate(), set::setRate);
    updateIfNotNull(dto.reps(), set::setReps);
    updateIfNotNull(dto.restSeconds(), set::setRestSeconds);
    updateIfNotNull(dto.weight(), set::setWeight);

    return trainingSetRepository.save(set);
  }

  @Transactional
  public String deleteSet(String setLocalId) {
    if (!trainingSetRepository.existsByLocalId(setLocalId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.set.not-found"));
    }
    trainingSetRepository.deleteByLocalId(setLocalId);
    return setLocalId;
  }
}
