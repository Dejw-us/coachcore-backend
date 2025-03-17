package pro.coachcore.training.plan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.training.plan.goal.TrainingGoal;
import pro.coachcore.training.plan.goal.TrainingGoalRepository;
import pro.coachcore.training.plan.unit.TrainingUnitRepository;

import static pro.coachcore.util.ServiceUtils.updateIfNotNull;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingPlanService {
  private final TrainingPlanRepository trainingPlanRepository;
  private final TrainingGoalRepository trainingGoalRepository;
  private final TrainingUnitRepository unitRepository;

  public List<TrainingPlan> findAllTrainingPlans() {
    return trainingPlanRepository.findAll();
  }

  public List<TrainingPlan> findAllUserTrainingPlans(Jwt jwt) {
    var userId = (String) jwt.getClaim("id");
    return trainingPlanRepository.findAllByCreatedBy(userId);
  }

  public TrainingPlan findTrainingPlanByLocalId(String localId) {
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

  @Transactional
  public void deleteTrainingPlanByLocalId(String planLocalId) {
    if (!trainingPlanRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException("Training plan does not exist");
    }

    var plan = findTrainingPlanByLocalId(planLocalId);

    plan.getUnits().forEach(unit -> log.debug("unit id: {}", unit.getId()));

    log.debug("Plan: {}", plan);

    trainingPlanRepository.delete(plan);
  }

  public boolean isTrainingPlanOwner(String userId, String planId) {
    return trainingPlanRepository.existsByLocalIdAndCreatedBy(planId, userId);
  }
}
