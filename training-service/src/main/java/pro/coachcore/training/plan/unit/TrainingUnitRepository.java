package pro.coachcore.training.plan.unit;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.coachcore.training.plan.TrainingPlan;

@Repository
public interface TrainingUnitRepository extends JpaRepository<TrainingUnit, Long> {
  List<TrainingUnit> findAllByTrainingPlan_LocalId(String localId);

  Optional<TrainingUnit> findByTrainingPlan_LocalIdAndLocalId(String planLocalId,
      String unitLocalId);

  Optional<TrainingUnit> findByTrainingPlan_LocalIdAndDayOfWeek(String planLocalId,
      DayOfWeek dayOfWeek);

  Optional<TrainingUnit> findByLocalId(String localId);

  void deleteByTrainingPlan_LocalIdAndLocalId(String trainingPlanLocalId, String localId);

  void deleteAllByTrainingPlan_LocalId(String planLocalId);

  boolean existsByTrainingPlan_LocalIdAndLocalId(String trainingPlanLocalId, String localId);

  boolean existsByLocalId(String localId);

  boolean existsByTrainingPlan_LocalIdAndDayOfWeek(String planLocalId, DayOfWeek dayOfWeek);

  boolean existsByTrainingPlanAndDayOfWeek(TrainingPlan trainingPlan, DayOfWeek dayOfWeek);

  long countByTrainingPlan_LocalIdAndDayOfWeek(String planLocalId, DayOfWeek dayOfWeek);
}
