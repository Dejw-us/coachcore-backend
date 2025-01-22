package pro.shapeit.backend.training.plan.goal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingGoalRepository extends JpaRepository<TrainingGoal, Long> {
}
