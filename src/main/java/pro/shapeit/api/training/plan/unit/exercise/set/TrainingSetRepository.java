package pro.shapeit.api.training.plan.unit.exercise.set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingSetRepository extends JpaRepository<TrainingSet, Long> {
  @Modifying
  @Query("DELETE FROM TrainingSet set WHERE set.localId = :localId")
  int deleteByLocalIdWithCount(@Param("localId") String localId);

  Optional<TrainingSet> findByLocalId(String localId);
}
