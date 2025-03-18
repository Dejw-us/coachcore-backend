package pro.coachcore.training.plan.owner;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;
import pro.coachcore.training.plan.TrainingPlan;
import pro.coachcore.training.plan.TrainingPlanRepository;

@Service
@RequiredArgsConstructor
public class TrainingPlanOwnerService {
  private final TrainingPlanOwnerRepository trainingPlanOwnerRepository;
  private final TrainingPlanRepository planRepository;
  private final MessageService messageService;

  @Transactional
  public boolean canView(String planLocalId, String userId) {
    if (!planRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.plan.not-found"));
    }
    return trainingPlanOwnerRepository.findByTrainingPlan_LocalIdAndUserId(planLocalId, userId)
        .map(owner -> owner.canView()).orElse(false);
  }

  public List<TrainingPlanOwner> getPlanOwners(String planLocalId) {
    return trainingPlanOwnerRepository.findAllByTrainingPlan_LocalId(planLocalId);
  }

  public List<TrainingPlanOwner> getPlanOwners(TrainingPlan plan) {
    return getPlanOwners(plan.getLocalId());
  }

}
