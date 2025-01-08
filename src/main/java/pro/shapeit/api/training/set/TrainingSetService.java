package pro.shapeit.api.training.set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}
