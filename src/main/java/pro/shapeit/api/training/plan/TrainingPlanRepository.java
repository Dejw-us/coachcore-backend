package pro.shapeit.api.training.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {
  Optional<TrainingPlan> findByLocalId(String localId);
}
