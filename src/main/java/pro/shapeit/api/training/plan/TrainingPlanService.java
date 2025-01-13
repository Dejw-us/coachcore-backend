package pro.shapeit.api.training.plan;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.goal.TrainingGoal;
import pro.shapeit.api.training.goal.TrainingGoalRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TrainingPlanService {
  private final TrainingPlanRepository trainingPlanRepository;
  private final TrainingGoalRepository trainingGoalRepository;

  public Boolean canViewTrainingPlan(TrainingPlan plan, Authentication authentication) {
    return true;
  }

  public Page<TrainingPlan> findTrainingPlans(int limit) {
    return trainingPlanRepository.findAll(PageRequest.of(0, limit));
  }

  public TrainingPlan findTrainingPlan(String localId) throws ResourceNotFoundException {
    return trainingPlanRepository.findByLocalId(localId)
        .orElseThrow(() -> new ResourceNotFoundException("Training plan does not exist"));
  }

  public List<TrainingGoal> findTrainingGoals(String trainingPlanLocalId) {
    return trainingGoalRepository.findByTrainingPlan_LocalId(trainingPlanLocalId);
  }


  public TrainingPlan saveTrainingPlan(CreateTrainingPlanDto dto) {
    var plan = new TrainingPlan();

    plan.setLocalId(UUID.randomUUID().toString());
    plan.setDescription(dto.description());
    plan.setName(dto.name());

    var savedPlan = trainingPlanRepository.save(plan);
    var goals = dto.goals().stream()
        .map(TrainingGoal::new)
        .peek(goal -> goal.setTrainingPlan(savedPlan))
        .toList();

    trainingGoalRepository.saveAll(goals);

    return savedPlan;
  }
}
