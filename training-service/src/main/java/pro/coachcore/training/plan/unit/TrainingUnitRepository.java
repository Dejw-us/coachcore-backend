package pro.coachcore.training.plan.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pro.coachcore.training.plan.TrainingPlan;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingUnitRepository extends JpaRepository<TrainingUnit, Long> {
  List<TrainingUnit> findAllByTrainingPlan_LocalId(String localId);

  void deleteAllByTrainingPlan_LocalId(String planLocalId);

  Optional<TrainingUnit> findByTrainingPlan_LocalIdAndLocalId(String planLocalId, String unitLocalId);

  Optional<TrainingUnit> findByTrainingPlan_LocalIdAndDayOfWeek(String planLocalId, DayOfWeek dayOfWeek);

  boolean existsByTrainingPlan_LocalIdAndLocalId(
      String trainingPlanLocalId,
      String localId);

  boolean existsByLocalId(String localId);

  Optional<TrainingUnit> findByLocalId(String localId);

  void deleteByTrainingPlan_LocalIdAndLocalId(
      String trainingPlanLocalId,
      String localId);

  boolean existsByTrainingPlanAndDayOfWeek(TrainingPlan trainingPlan, DayOfWeek dayOfWeek);

  boolean existsByTrainingPlan_LocalIdAndDayOfWeek(String planLocalId, DayOfWeek dayOfWeek);
}
