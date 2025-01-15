package pro.shapeit.api.training.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.plan.goal.TrainingGoal;
import pro.shapeit.api.training.plan.unit.TrainingUnit;
import pro.shapeit.api.training.plan.unit.TrainingUnitRepository;

import java.util.List;

import static pro.shapeit.api.common.util.ServiceUtils.updateIfNotNull;

@Service
@RequiredArgsConstructor
class TrainingPlanService {
  private final TrainingPlanRepository trainingPlanRepository;
  private final TrainingUnitRepository trainingUnitRepository;

  // --- Find methods ---

  List<TrainingPlan> findAllTrainingPlans() {
    return trainingPlanRepository.findAll();
  }

  TrainingPlan findTrainingPlanByLocalId(String localId) throws ResourceNotFoundException {
    return trainingPlanRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Training plan does not exist"));
  }

  List<TrainingUnit> findAllTrainingUnitsByPlanLocalId(String planLocalId) throws ResourceNotFoundException {
    if (!trainingPlanRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException("Training plan does not exist");
    }
    return trainingUnitRepository.findAllByTrainingPlan_LocalId(planLocalId);
  }

  // --- Save methods ---

  TrainingPlan saveTrainingPlan(CreateTrainingPlanDto dto) {
    var plan = new TrainingPlan();

    plan.setName(dto.name());
    plan.setDescription(dto.description());
    plan.setGoals(dto.goals().stream().map(TrainingGoal::new).toList());

    return trainingPlanRepository.save(plan);
  }

  // --- Update methods ---

  TrainingPlan updateTrainingPlan(TrainingPlan plan, UpdateTrainingPlanDto dto) {
    updateIfNotNull(dto.name(), plan::setName);
    updateIfNotNull(dto.description(), plan::setDescription);

    return trainingPlanRepository.save(plan);
  }

  // --- Delete methods ---

  boolean deleteTrainingPlanByLocalId(String planLocalId) {
    return trainingPlanRepository.deleteByLocalIdWithCount(planLocalId) > 0;
  }
}
