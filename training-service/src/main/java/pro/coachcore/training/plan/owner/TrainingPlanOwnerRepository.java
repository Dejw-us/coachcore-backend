package pro.coachcore.training.plan.owner;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingPlanOwnerRepository extends JpaRepository<TrainingPlanOwner, Long> {
  public List<TrainingPlanOwner> findAllByTrainingPlan_LocalId(String planLocalId);

  public Optional<TrainingPlanOwner> findByTrainingPlan_LocalIdAndUserId(String planLocalId,
      String userId);
}
