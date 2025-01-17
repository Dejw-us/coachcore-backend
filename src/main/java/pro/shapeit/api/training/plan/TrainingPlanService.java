package pro.shapeit.api.training.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.catalog.exercise.CatalogExercise;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.plan.goal.TrainingGoal;
import pro.shapeit.api.training.plan.goal.TrainingGoalRepository;
import pro.shapeit.api.training.plan.unit.CreateTrainingUnitDto;
import pro.shapeit.api.training.plan.unit.TrainingUnit;
import pro.shapeit.api.training.plan.unit.TrainingUnitRepository;
import pro.shapeit.api.training.plan.unit.UpdateTrainingUnitDto;
import pro.shapeit.api.training.plan.unit.exercise.TrainingExercise;
import pro.shapeit.api.training.plan.unit.exercise.TrainingExerciseRepository;
import pro.shapeit.api.training.plan.unit.exercise.UpdateTrainingExerciseDto;
import pro.shapeit.api.training.plan.unit.exercise.set.TrainingSet;
import pro.shapeit.api.training.plan.unit.exercise.set.TrainingSetRepository;
import pro.shapeit.api.training.plan.unit.exercise.set.UpdateTrainingSetDto;

import java.util.List;

import static pro.shapeit.api.common.util.ServiceUtils.updateIfNotNull;

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
        .orElseThrow(ResourceNotFoundException.supplier("Training plan does not exist"));
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
