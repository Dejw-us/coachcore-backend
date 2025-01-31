package pro.shapeit.training.plan.unit.exercise.set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingSetRepository extends JpaRepository<TrainingSet, Long> {
  boolean existsByLocalId(String localId);

  void deleteByLocalId(String localId);

  Optional<TrainingSet> findByLocalId(String localId);
}
