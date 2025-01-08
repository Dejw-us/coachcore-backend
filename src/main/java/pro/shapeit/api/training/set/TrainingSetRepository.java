package pro.shapeit.api.training.set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingSetRepository extends JpaRepository<TrainingSet, Long> {
  List<TrainingSet> findByTrainingExercise_LocalId(String localId);

  Optional<TrainingSet> findByLocalId(String localId);
}
