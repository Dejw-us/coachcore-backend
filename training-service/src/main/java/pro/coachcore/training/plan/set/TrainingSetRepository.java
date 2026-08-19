package pro.coachcore.training.plan.set;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingSetRepository extends JpaRepository<TrainingSet, Long> {
  boolean existsByLocalId(String localId);

  long countByTrainingExercise_LocalId(String localId);

  void deleteAllByTrainingExercise_LocalId(String localId);

  void deleteByLocalId(String localId);

  List<TrainingSet> findAllByTrainingExercise_LocalId(String localId);

  Optional<TrainingSet> findByLocalId(String localId);
}
