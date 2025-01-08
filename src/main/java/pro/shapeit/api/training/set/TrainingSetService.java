package pro.shapeit.api.training.set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.common.id.LocalId;
import pro.shapeit.api.training.exercise.TrainingExercise;

@Service
@RequiredArgsConstructor
public class TrainingSetService {
  private final TrainingSetRepository trainingSetRepository;

  public TrainingSet saveTrainingSet(TrainingExercise parent) {
    var set = new TrainingSet();

    set.setTrainingExercise(parent);
    set.setLocalId(LocalId.random());

    return trainingSetRepository.save(set);
  }

  public TrainingSet findTrainingSet(String localId) throws ResourceNotFoundException {
    return trainingSetRepository.findByLocalId(localId)
        .orElseThrow(() -> new ResourceNotFoundException("Training set does not exist"));
  }

  public TrainingSet updateTrainingSet(TrainingSet set, UpdateTrainingSetDto dto) {
    if (dto.intensityType() != null) {
      set.setIntensity(dto.intensity());
    }
    if (dto.rate() != null) {
      set.setRate(dto.rate());
    }
    if (dto.reps() != null) {
      set.setReps(dto.reps());
    }
    if (dto.rest() != null) {
      set.setRest(dto.rest());
    }
    if (dto.intensity() != null) {
      set.setIntensity(dto.intensity());
    }

    return trainingSetRepository.save(set);
  }
}
