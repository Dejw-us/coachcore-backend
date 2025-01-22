package pro.shapeit.backend.training.plan.unit.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingExerciseRepository extends JpaRepository<TrainingExercise, Long> {
  @Modifying
  @Query("DELETE FROM TrainingExercise exercise WHERE exercise.localId = :localId")
  int deleteByLocalIdWithCount(@Param("localId") String localId);

  Optional<TrainingExercise> findByLocalId(String localId);
}
