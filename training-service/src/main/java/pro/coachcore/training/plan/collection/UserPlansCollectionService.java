package pro.coachcore.training.plan.collection;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import pro.coachcore.exception.GlobalHandlerRuntimeException;
import pro.coachcore.training.plan.TrainingPlan;

public interface UserPlansCollectionService<T, ID> {
  T savePlan(TrainingPlan plan);

  void removePlan(ID id);

  Page<TrainingPlan> getPlans(Pageable pageable);

  Long getUsersAmount(ID id);

  default AuditorAware<String> getAuditorAware() {
    throw new UnsupportedOperationException("You have to implement #getAuditorAware to use #getAudtior");
  }

  default String getAuditor() {
    return getAuditorAware().getCurrentAuditor().orElseThrow(() -> GlobalHandlerRuntimeException.create(
        "You have to be authorized using jwt", HttpStatus.UNAUTHORIZED, "NO_JWT"));
  }
}
