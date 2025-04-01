package pro.coachcore.training.plan.save;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedPlanRepository extends JpaRepository<SavedPlan, Long> {
  List<SavedPlan> findAllByUserId(String userId);

  boolean existsBySavedPlan_LocalIdAndUserId(String planLocalId, String userId);

  long countBySavedPlan_LocalId(String planLocalId);

  void deleteBySavedPlan_LocalIdAndUserId(String planLocalId, String userId);
}
