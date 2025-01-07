package pro.shapeit.api.training.plan;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.goal.TrainingGoal;
import pro.shapeit.api.training.goal.TrainingGoalDto;
import pro.shapeit.api.training.goal.TrainingGoalMapper;
import pro.shapeit.api.training.goal.TrainingGoalRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TrainingPlanService {
  private final TrainingPlanRepository trainingPlanRepository;
  private final TrainingGoalRepository trainingGoalRepository;
  private final TrainingPlanMapper trainingPlanMapper;
  private final TrainingGoalMapper trainingGoalMapper;



  public List<TrainingPlanDto> findTrainingPlans(int limit) {
    return trainingPlanRepository.findAll(PageRequest.of(0, limit))
        .map(trainingPlanMapper::map)
        .toList();
  }

  public TrainingPlan findTrainingPlan(String localId) throws ResourceNotFoundException {
    return trainingPlanRepository.findByLocalId(localId)
        .orElseThrow(() -> new ResourceNotFoundException("Training plan does not exist"));
  }

  public List<TrainingGoalDto> findTrainingGoals(String trainingPlanLocalId) {
    return trainingGoalRepository.findByTrainingPlan_LocalId(trainingPlanLocalId).stream()
        .map(trainingGoalMapper::map)
        .toList();
  }

  public TrainingPlanDto saveTrainingPlan(CreateTrainingPlanDto dto) {
    var plan = new TrainingPlan();
    var goals = dto.goals().stream().map(TrainingGoal::new).toList();

    plan.setLocalId(UUID.randomUUID().toString());
    plan.setDescription(dto.description());
    plan.setGoals(goals);
    plan.setName(dto.name());

    trainingGoalRepository.saveAll(goals);
    trainingPlanRepository.save(plan);

    return trainingPlanMapper.map(plan);
  }
}
