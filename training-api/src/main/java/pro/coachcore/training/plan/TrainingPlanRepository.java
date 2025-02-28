package pro.coachcore.training.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {
  Optional<TrainingPlan> findByLocalId(String localId);

  boolean existsByLocalId(String localId);

  List<TrainingPlan> findAllByCreatedBy(String createdBy);

  void deleteByLocalId(String localId);

  boolean existsByLocalIdAndCreatedBy(String localId, String createdBy);
}
