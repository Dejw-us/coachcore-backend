package pro.shapeit.api.training.plan.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingUnitRepository extends JpaRepository<TrainingUnit, Long> {
  List<TrainingUnit> findAllByTrainingPlan_LocalId(String localId);
}
