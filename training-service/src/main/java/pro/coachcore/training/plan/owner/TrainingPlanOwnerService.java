package pro.coachcore.training.plan.owner;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;
import pro.coachcore.training.plan.TrainingPlan;
import pro.coachcore.training.plan.TrainingPlanRepository;

/**
 * Service class responsible for handling operations related to the owners of training plans.
 * Provides methods to check user permissions and retrieve owners of a training plan.
 */
@Service
@RequiredArgsConstructor
public class TrainingPlanOwnerService {
  private final TrainingPlanOwnerRepository trainingPlanOwnerRepository;
  private final TrainingPlanRepository planRepository;
  private final MessageService messageService;

  /**
   * Checks if a user has view permission for a specific training plan.
   *
   * @param planLocalId the local ID of the training plan.
   * @param userId the ID of the user.
   * @return true if the user has permission to view the plan, false otherwise.
   * @throws ResourceNotFoundException if the training plan does not exist.
   */
  @Transactional
  public boolean canView(String planLocalId, String userId) {
    if (!planRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.plan.not-found"));
    }
    return trainingPlanOwnerRepository.findByTrainingPlan_LocalIdAndUserId(planLocalId, userId)
        .map(owner -> owner.canView()).orElse(false);
  }

  /**
   * Retrieves the list of owners for a specific training plan identified by its local ID.
   *
   * @param planLocalId the local ID of the training plan.
   * @return a list of TrainingPlanOwner objects for the specified plan.
   */
  public List<TrainingPlanOwner> getPlanOwners(String planLocalId) {
    return trainingPlanOwnerRepository.findAllByTrainingPlan_LocalId(planLocalId);
  }

  /**
   * Retrieves the list of owners for a specific training plan.
   *
   * @param plan the TrainingPlan object.
   * @return a list of TrainingPlanOwner objects for the specified plan.
   */
  public List<TrainingPlanOwner> getPlanOwners(TrainingPlan plan) {
    return getPlanOwners(plan.getLocalId());
  }
}
