package pro.shapeit.api.training.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {
  Optional<TrainingPlan> findByLocalId(String localId);

  boolean existsByLocalId(String localId);

  void deleteByLocalId(String localId);

  @Modifying
  @Query("DELETE FROM TrainingPlan plan WHERE plan.localId = :localId")
  int deleteByLocalIdWithCount(@Param("localId") String localId);

  boolean existsByLocalIdAndCreatedBy(String localId, String createdBy);
}
