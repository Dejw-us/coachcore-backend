package pro.shapeit.training.plan.unit.exercise.set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.common.exception.ResourceNotFoundException;
import pro.shapeit.training.plan.unit.exercise.TrainingExercise;
import pro.shapeit.training.plan.unit.exercise.TrainingExerciseRepository;

import static org.apache.commons.lang3.EnumUtils.getEnum;
import static pro.shapeit.common.util.ServiceUtils.updateIfNotNull;

@Service
@RequiredArgsConstructor
public class TrainingSetService {
  private final TrainingSetRepository trainingSetRepository;
  private final TrainingExerciseRepository trainingExerciseRepository;

  public TrainingSet findTrainingSetByLocalId(String localId) throws ResourceNotFoundException {
    return trainingSetRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Training set does not exist"));
  }

  public TrainingSet saveTrainingSet(TrainingExercise exercise) {
    var savedSet = trainingSetRepository.save(new TrainingSet());
    exercise.getSets().add(savedSet);
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

  public boolean deleteTrainingSetByLocalId(String localId) {
    return trainingSetRepository.deleteByLocalIdWithCount(localId) > 0;
  }
}
