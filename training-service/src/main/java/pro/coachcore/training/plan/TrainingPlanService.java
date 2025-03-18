package pro.coachcore.training.plan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pro.coachcore.dto.DeletedObjectDto;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;
import pro.coachcore.training.plan.goal.TrainingGoal;
import pro.coachcore.training.plan.goal.TrainingGoalRepository;
import pro.coachcore.training.plan.owner.TrainingPlanOwner;
import pro.coachcore.training.plan.owner.TrainingPlanOwnerRepository;
import pro.coachcore.training.plan.owner.TrainingPlanOwner.Permission;
import static pro.coachcore.util.ServiceUtils.updateIfNotNull;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingPlanService {
  private final TrainingPlanRepository trainingPlanRepository;
  private final TrainingGoalRepository trainingGoalRepository;
  private final TrainingPlanOwnerRepository trainingPlanOwnerRepository;
  private final MessageService messageService;

  public List<TrainingPlan> findAllTrainingPlans() {
    return trainingPlanRepository.findAll();
  }

  public List<TrainingPlan> findAllUserTrainingPlans(Jwt jwt) {
    var userId = (String) jwt.getClaim("id");
    return trainingPlanRepository.findAllByCreatedBy(userId);
  }

  public TrainingPlan findTrainingPlanByLocalId(String localId) {
    return trainingPlanRepository.findByLocalId(localId).orElseThrow(
        ResourceNotFoundException.supplier(messageService.getMessage("training.plan.not-found")));
  }

  public TrainingPlan saveTrainingPlan(CreateTrainingPlanDto dto) {
    var plan = new TrainingPlan();
    var goals = dto.goals().stream().map(TrainingGoal::new).toList();
    var savedGoals = trainingGoalRepository.saveAll(goals);

    plan.setName(dto.name());
    plan.setDescription(dto.description());
    plan.setGoals(savedGoals);

    var savedPlan = trainingPlanRepository.save(plan);

    var owner = new TrainingPlanOwner();

    owner.setUserId(savedPlan.getCreatedBy());
    owner.setTrainingPlan(plan);
    owner.addPermission(Permission.ADMIN);

    trainingPlanOwnerRepository.save(owner);

    return savedPlan;
  }

  public TrainingPlan updateTrainingPlan(TrainingPlan plan, UpdateTrainingPlanDto dto) {
    updateIfNotNull(dto.name(), plan::setName);
    updateIfNotNull(dto.description(), plan::setDescription);

    return trainingPlanRepository.save(plan);
  }

  @Transactional
  public DeletedObjectDto deleteTrainingPlanByLocalId(String planLocalId) {
    if (!trainingPlanRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.plan.not-found"));
    }

    var plan = findTrainingPlanByLocalId(planLocalId);
    trainingPlanRepository.delete(plan);

    return new DeletedObjectDto(planLocalId);
  }
}
