package pro.shapeit.training.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.backend.common.exception.resource.ResourceNotFoundException;
import pro.shapeit.backend.training.plan.goal.TrainingGoal;
import pro.shapeit.backend.training.plan.goal.TrainingGoalRepository;

import java.util.List;

import static pro.shapeit.backend.common.util.ServiceUtils.updateIfNotNull;

@Service
@RequiredArgsConstructor
public class TrainingPlanService {
  private final TrainingPlanRepository trainingPlanRepository;
  private final TrainingGoalRepository trainingGoalRepository;

  public List<TrainingPlan> findAllTrainingPlans() {
    return trainingPlanRepository.findAll();
  }

  public TrainingPlan findTrainingPlanByLocalId(
      String localId
  ) throws ResourceNotFoundException {
    return trainingPlanRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Training pro.shapeit.plan does not exist"));
  }

  public TrainingPlan saveTrainingPlan(CreateTrainingPlanDto dto) {
    var plan = new TrainingPlan();
    var goals = dto.goals().stream()
        .map(TrainingGoal::new)
        .toList();
    var savedGoals = trainingGoalRepository.saveAll(goals);

    plan.setName(dto.name());
    plan.setDescription(dto.description());
    plan.setGoals(savedGoals);

    return trainingPlanRepository.save(plan);
  }

  public TrainingPlan updateTrainingPlan(TrainingPlan plan, UpdateTrainingPlanDto dto) {
    updateIfNotNull(dto.name(), plan::setName);
    updateIfNotNull(dto.description(), plan::setDescription);

    return trainingPlanRepository.save(plan);
  }

  public boolean deleteTrainingPlanByLocalId(String planLocalId) {
    return trainingPlanRepository.deleteByLocalIdWithCount(planLocalId) > 0;
  }

  public boolean isTrainingPlanOwner(String userId, String planId) {
    return trainingPlanRepository.existsByLocalIdAndCreatedBy(planId, userId);
  }
}
