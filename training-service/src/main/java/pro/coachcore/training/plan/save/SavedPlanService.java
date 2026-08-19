package pro.coachcore.training.plan.save;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.training.plan.TrainingPlan;
import pro.coachcore.training.plan.collection.UserPlansCollectionService;

@Service
@RequiredArgsConstructor
public class SavedPlanService implements UserPlansCollectionService<SavedPlan, String> {
  private final SavedPlanRepository savedPlanRepository;

  @Getter
  private final AuditorAware<String> auditorAware;

  public SavedPlan savePlan(TrainingPlan plan) {
    if (savedPlanRepository.existsBySavedPlan_LocalIdAndUserId(plan.getLocalId(), getAuditor())) {
      throw new ResourceAlreadyExistsException("Plan is already saved");
    }
    var savedPlan = new SavedPlan();
    savedPlan.setSavedPlan(plan);
    return savedPlanRepository.save(savedPlan);
  }

  @Transactional
  public void removePlan(String planId) {
    savedPlanRepository.deleteBySavedPlan_LocalIdAndUserId(planId, getAuditor());
  }

  public Page<TrainingPlan> getPlans(Pageable pageable) {
    return savedPlanRepository.findAllByUserId(getAuditor(), pageable)
        .map(SavedPlan::getSavedPlan);
  }

  public Long getUsersAmount(String planLocalId) {
    return savedPlanRepository.countBySavedPlan_LocalId(planLocalId);
  }
}
