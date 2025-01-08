package pro.shapeit.api.training.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingExerciseRepository extends JpaRepository<TrainingExercise, Long> {
  Optional<TrainingExercise> findByLocalId(String localId);
}
