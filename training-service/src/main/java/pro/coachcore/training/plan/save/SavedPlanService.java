package pro.coachcore.training.plan.save;

import java.util.List;

import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.GlobalHandlerRuntimeException;
import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.training.plan.TrainingPlan;

@Service
@RequiredArgsConstructor
public class SavedPlanService {
  private final SavedPlanRepository savedPlanRepository;
  private final AuditorAware<String> auditorAware;

  public SavedPlan savePlan(TrainingPlan plan) {
    if (savedPlanRepository.existsBySavedPlan_LocalIdAndUserId(plan.getLocalId(), getUserId())) {
      throw new ResourceAlreadyExistsException("Plan is already saved");
    }
    var savedPlan = new SavedPlan();
    savedPlan.setSavedPlan(plan);
    return savedPlanRepository.save(savedPlan);
  }

  @Transactional
  public void removeSavedPlan(String planId) {
    savedPlanRepository.deleteBySavedPlan_LocalIdAndUserId(planId, getUserId());
  }

  public List<SavedPlan> getSavedPlans() {
    var userId = getUserId();
    return savedPlanRepository.findAllByUserId(userId);
  }

  public long getUsersAmount(String planLocalId) {
    return savedPlanRepository.countBySavedPlan_LocalId(planLocalId);
  }

  private String getUserId() {
    return auditorAware.getCurrentAuditor().orElseThrow(() -> GlobalHandlerRuntimeException.create(
        "You have to be authorized using jwt", HttpStatus.UNAUTHORIZED, "NO_JWT"));
  }
}
