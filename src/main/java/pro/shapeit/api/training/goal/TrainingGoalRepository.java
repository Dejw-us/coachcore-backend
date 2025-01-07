package pro.shapeit.api.training.goal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingGoalRepository extends JpaRepository<TrainingGoal, Long> {
  List<TrainingGoal> findByTrainingPlan_LocalId(String localId);
}
