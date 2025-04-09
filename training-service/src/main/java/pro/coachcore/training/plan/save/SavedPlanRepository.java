package pro.coachcore.training.plan.save;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedPlanRepository extends JpaRepository<SavedPlan, Long> {
  Page<SavedPlan> findAllByUserId(String userId, Pageable pageable);

  boolean existsBySavedPlan_LocalIdAndUserId(String planLocalId, String userId);

  long countBySavedPlan_LocalId(String planLocalId);

  void deleteBySavedPlan_LocalIdAndUserId(String planLocalId, String userId);
}
