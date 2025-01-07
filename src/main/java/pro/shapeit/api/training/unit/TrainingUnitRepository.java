package pro.shapeit.api.training.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingUnitRepository extends JpaRepository<TrainingUnit, Long> {
  List<TrainingUnit> findByTrainingPlan_LocalId(String trainingPlanLocalId);
  Optional<TrainingUnit> findByLocalId(String localId);
}
