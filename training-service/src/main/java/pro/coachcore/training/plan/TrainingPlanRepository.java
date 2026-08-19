package pro.coachcore.training.plan;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {
  Optional<TrainingPlan> findByLocalId(String localId);

  Page<TrainingPlan> findAllByCreatedBy(String createdBy, Pageable pageable);

  List<TrainingPlan> findAllByIsPublic(Boolean isPublic);

  boolean existsByLocalId(String localId);

  boolean existsByLocalIdAndCreatedBy(String localId, String createdBy);

  void deleteByLocalId(String localId);
}
