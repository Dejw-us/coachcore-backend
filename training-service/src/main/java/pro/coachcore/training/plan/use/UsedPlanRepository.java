package pro.coachcore.training.plan.use;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedPlanRepository extends JpaRepository<UsedPlan, Long> {
  boolean existsByUsedPlan_LocalIdAndUserId(String usedPlanLocalId, String userId);

  void deleteByUsedPlan_LocalIdAndUserId(String usedPlanLocalId, String userId);

  Page<UsedPlan> findAllByUserId(String userId, Pageable pageable);

  long countByUsedPlan_LocalId(String usedPlanLocalId);
}
