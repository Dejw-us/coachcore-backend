package pro.coachcore.training.plan;

import static pro.coachcore.util.ServiceUtils.updateIfNotNull;
import java.util.List;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.dto.DeletedObjectDto;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;
import pro.coachcore.training.plan.goal.TrainingGoal;
import pro.coachcore.training.plan.goal.TrainingGoalRepository;
import pro.coachcore.training.plan.owner.TrainingPlanOwner;
import pro.coachcore.training.plan.owner.TrainingPlanOwner.Permission;
import pro.coachcore.training.plan.owner.TrainingPlanOwnerRepository;

/**
 * Service class responsible for handling operations related to training plans. Provides methods to
 * retrieve, create, update, and delete training plans, as well as manage associated goals and
 * owners.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingPlanService {
  private final TrainingPlanRepository trainingPlanRepository;
  private final TrainingGoalRepository trainingGoalRepository;
  private final TrainingPlanOwnerRepository trainingPlanOwnerRepository;
  private final AuditorAware<String> auditorAware;
  private final MessageService messageService;

  /**
   * Retrieves all training plans.
   *
   * @return a list of all training plans.
   */
  public List<TrainingPlan> getAllPlans() {
    return trainingPlanRepository.findAll();
  }

  /**
   * Retrieves all training plans created by the current user.
   *
   * @return a list of training plans created by the current user.
   */
  public List<TrainingPlan> getUserPlans() {
    return trainingPlanRepository
        .findAllByCreatedBy(auditorAware.getCurrentAuditor().orElseThrow());
  }

  /**
   * Retrieves a training plan by its local ID.
   *
   * @param planLocalId the local ID of the training plan.
   * @return the training plan with the specified local ID.
   * @throws ResourceNotFoundException if the plan with the specified ID is not found.
   */
  public TrainingPlan getPlan(String planLocalId) {
    return trainingPlanRepository.findByLocalId(planLocalId).orElseThrow(
        ResourceNotFoundException.supplier(messageService.getMessage("training.plan.not-found")));
  }

  /**
   * Saves a new training plan.
   *
   * @param dto the DTO containing the details of the training plan to be created.
   * @return the created training plan.
   */
  public TrainingPlan savePlan(CreateTrainingPlanDto dto) {
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

  /**
   * Updates an existing training plan.
   *
   * @param plan the existing training plan to be updated.
   * @param dto the DTO containing the updates for the training plan.
   * @return the updated training plan.
   */
  public TrainingPlan updatePlan(TrainingPlan plan, UpdateTrainingPlanDto dto) {
    updateIfNotNull(dto.name(), plan::setName);
    updateIfNotNull(dto.description(), plan::setDescription);

    return trainingPlanRepository.save(plan);
  }

  /**
   * Deletes a training plan by its local ID.
   *
   * @param planLocalId the local ID of the training plan to be deleted.
   * @return a DTO containing the ID of the deleted training plan.
   * @throws ResourceNotFoundException if the plan with the specified ID is not found.
   */
  @Transactional
  public DeletedObjectDto deletePlan(String planLocalId) {
    if (!trainingPlanRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.plan.not-found"));
    }

    var plan = getPlan(planLocalId);
    trainingPlanRepository.delete(plan);

    return new DeletedObjectDto(planLocalId);
  }
}
