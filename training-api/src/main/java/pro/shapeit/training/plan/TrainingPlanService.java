package pro.shapeit.training.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import pro.coachcore.exception.ResourceNotFoundException;
import pro.shapeit.training.plan.goal.TrainingGoal;
import pro.shapeit.training.plan.goal.TrainingGoalRepository;

import static pro.coachcore.util.ServiceUtils.updateIfNotNull;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingPlanService {
  private final TrainingPlanRepository trainingPlanRepository;
  private final TrainingGoalRepository trainingGoalRepository;

  public List<TrainingPlan> findAllTrainingPlans() {
    return trainingPlanRepository.findAll();
  }

  public TrainingPlan findTrainingPlanByLocalId(String localId) {
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

  public void deleteTrainingPlanByLocalId(String planLocalId) {
    if (!trainingPlanRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException("Training plan does not exist");
    }
    trainingPlanRepository.deleteByLocalId(planLocalId);
  }

  public boolean isTrainingPlanOwner(String userId, String planId) {
    return trainingPlanRepository.existsByLocalIdAndCreatedBy(planId, userId);
  }
}
