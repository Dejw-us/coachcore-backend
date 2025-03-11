package pro.coachcore.training.plan.set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.training.plan.exercise.TrainingExercise;
import pro.coachcore.training.plan.exercise.TrainingExerciseRepository;

import static org.apache.commons.lang3.EnumUtils.getEnum;
import static pro.coachcore.util.ServiceUtils.updateIfNotNull;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingSetService {
  private final TrainingSetRepository trainingSetRepository;
  private final TrainingExerciseRepository trainingExerciseRepository;

  public TrainingSet findTrainingSetByLocalId(String localId) {
    return trainingSetRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Training set does not exist"));
  }

  public List<TrainingSet> findAllByTrainingExerciseLocalId(String localId) {
    if (!trainingExerciseRepository.existsByLocalId(localId)) {
      throw new ResourceNotFoundException("Training exercise does not exist");
    }
    return trainingSetRepository.findAllByTrainingExercise_LocalId(localId);
  }

  public TrainingSet saveTrainingSet(TrainingExercise exercise) {
    var set = new TrainingSet();
    set.setTrainingExercise(exercise);
    set.setTrainingExercise(exercise);
    var savedSet = trainingSetRepository.save(set);
    trainingExerciseRepository.save(exercise);
    return savedSet;
  }

  public TrainingSet updateTrainingSet(TrainingSet set, UpdateTrainingSetDto dto) {
    updateIfNotNull(dto.intensity(), set::setIntensity);
    updateIfNotNull(dto.rate(), set::setRate);
    updateIfNotNull(getEnum(TrainingSet.IntensityType.class, dto.intensityType()), set::setIntensityType);
    updateIfNotNull(dto.reps(), set::setReps);
    updateIfNotNull(dto.restSeconds(), set::setRestSeconds);
    updateIfNotNull(dto.weight(), set::setWeight);
    updateIfNotNull(getEnum(TrainingSet.WeightType.class, dto.weightType()), set::setWeightType);

    return trainingSetRepository.save(set);
  }

  @Transactional
  public void deleteTrainingSetByLocalId(String localId) {
    if (!trainingSetRepository.existsByLocalId(localId)) {
      throw new ResourceNotFoundException("Training set does not exist");
    }
    trainingSetRepository.deleteByLocalId(localId);
  }
}
