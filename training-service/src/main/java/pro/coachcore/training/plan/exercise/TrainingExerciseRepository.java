package pro.coachcore.training.plan.exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingExerciseRepository extends JpaRepository<TrainingExercise, Long> {
  boolean existsByLocalId(String localId);

  long countByTrainingUnit_LocalId(String localId);

  void deleteByLocalId(String localId);

  Optional<TrainingExercise> findByLocalId(String localId);

  List<TrainingExercise> findAllByTrainingUnit_LocalId(String unitLocalId);

  List<TrainingExercise> findAllByTrainingUnit_TrainingPlan_LocalId(String planLocalId);
}
