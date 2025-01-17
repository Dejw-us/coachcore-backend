package pro.shapeit.api.training.plan.unit.exercise.set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.plan.unit.exercise.TrainingExercise;
import pro.shapeit.api.training.plan.unit.exercise.TrainingExerciseRepository;

import static pro.shapeit.api.common.util.ServiceUtils.updateIfNotNull;

@Service
@RequiredArgsConstructor
public class TrainingSetService {
  private final TrainingSetRepository trainingSetRepository;
  private final TrainingExerciseRepository trainingExerciseRepository;

  // --- Find methods ---

  public TrainingSet findTrainingSetByLocalId(String localId) throws ResourceNotFoundException {
    return trainingSetRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Training set does not exist"));
  }

  // --- Save methods ---

  public TrainingSet saveTrainingSet(TrainingExercise exercise) {
    var savedSet = trainingSetRepository.save(new TrainingSet());
    exercise.getSets().add(savedSet);
    trainingExerciseRepository.save(exercise);
    return savedSet;
  }

  // --- Update methods ---

  public TrainingSet updateTrainingSet(TrainingSet set, UpdateTrainingSetDto dto) {
    updateIfNotNull(dto.intensity(), set::setIntensity);
    updateIfNotNull(dto.rate(), set::setRate);
    updateIfNotNull(dto.intensityType(), set::setIntensityType);
    updateIfNotNull(dto.reps(), set::setReps);
    updateIfNotNull(dto.rest(), set::setRest);
    updateIfNotNull(dto.weight(), set::setWeight);
    updateIfNotNull(dto.weightType(), set::setWeightType);

    return trainingSetRepository.save(set);
  }

  // --- Delete methods ---

  public boolean deleteTrainingSetByLocalId(String localId) {
    return trainingSetRepository.deleteByLocalIdWithCount(localId) > 0;
  }
}
