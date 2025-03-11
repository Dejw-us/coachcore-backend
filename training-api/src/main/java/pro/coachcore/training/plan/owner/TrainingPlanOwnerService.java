package pro.coachcore.training.plan.owner;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pro.coachcore.training.plan.TrainingPlan;

@Service
@RequiredArgsConstructor
public class TrainingPlanOwnerService {
  private final TrainingPlanOwnerRepository trainingPlanOwnerRepository;

  public boolean canView(String planLocalId, String userId) {
    return trainingPlanOwnerRepository.findByTrainingPlan_LocalIdAndUserId(planLocalId, userId)
        .map(owner -> owner.canView())
        .orElse(false);
  }

  public List<TrainingPlanOwner> getPlanOwners(String planLocalId) {
    return trainingPlanOwnerRepository.findAllByTrainingPlan_LocalId(planLocalId);
  }

  public List<TrainingPlanOwner> getPlanOwners(TrainingPlan plan) {
    return getPlanOwners(plan.getLocalId());
  }

}
