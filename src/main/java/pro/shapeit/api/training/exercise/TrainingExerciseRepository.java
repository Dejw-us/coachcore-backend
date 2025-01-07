package pro.shapeit.api.training.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingExerciseRepository extends JpaRepository<TrainingExercise, Long> {
}
