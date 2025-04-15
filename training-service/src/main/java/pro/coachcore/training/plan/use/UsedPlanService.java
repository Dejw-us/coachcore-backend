package pro.coachcore.training.plan.use;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.training.plan.TrainingPlan;
import pro.coachcore.training.plan.collection.UserPlansCollectionService;

@Service
@RequiredArgsConstructor
public class UsedPlanService implements UserPlansCollectionService<UsedPlan, String> {
  private final UsedPlanRepository usedPlanRepository;

  @Getter
  private final AuditorAware<String> auditorAware;

  @Override
  public UsedPlan savePlan(TrainingPlan plan) { // TODO Fix mulitple uses
    var usedPlan = new UsedPlan(plan);
    return usedPlanRepository.save(usedPlan);
  }

  @Override
  public void removePlan(String id) {
    if (!usedPlanRepository.existsByUsedPlan_LocalIdAndUserId(id, getAuditor())) {
      throw new ResourceNotFoundException("User is not using this plan or plan does not exist");
    }
    usedPlanRepository.deleteByUsedPlan_LocalIdAndUserId(id, getAuditor());
  }

  @Override
  public Page<TrainingPlan> getPlans(Pageable pageable) {
    return usedPlanRepository.findAllByUserId(getAuditor(), pageable)
        .map(UsedPlan::getUsedPlan);
  }

  @Override
  public Long getUsersAmount(String id) {
    return usedPlanRepository.countByUsedPlan_LocalId(id);
  }
}
