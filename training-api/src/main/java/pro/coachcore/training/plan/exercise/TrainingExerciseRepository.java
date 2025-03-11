package pro.coachcore.training.plan.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingExerciseRepository extends JpaRepository<TrainingExercise, Long> {
  boolean existsByLocalId(String localId);

  void deleteByLocalId(String localId);

  Optional<TrainingExercise> findByLocalId(String localId);

  List<TrainingExercise> findAllByTrainingUnit_LocalId(String unitLocalId);
}
