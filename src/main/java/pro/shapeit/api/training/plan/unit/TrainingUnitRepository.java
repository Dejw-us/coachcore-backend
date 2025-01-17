package pro.shapeit.api.training.plan.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pro.shapeit.api.training.plan.TrainingPlan;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingUnitRepository extends JpaRepository<TrainingUnit, Long> {
  List<TrainingUnit> findAllByTrainingPlan_LocalId(String localId);

  Optional<TrainingUnit> findByTrainingPlan_LocalIdAndLocalId(String planLocalId, String unitLocalId);

  @Modifying
  @Query("DELETE FROM TrainingUnit unit WHERE unit.trainingPlan.localId = :planLocalId AND unit.localId = :unitLocalId")
  int deleteByTrainingPlan_LocalIdAndLocalIdWithCount(
      @Param("planLocalId") String planLocalId,
      @Param("unitLocalId") String unitLocalId
  );

  boolean existsByTrainingPlanAndDayOfWeek(TrainingPlan trainingPlan, DayOfWeek dayOfWeek);

  @Query("""
      SELECT COUNT(unit) > 0
      FROM TrainingUnit unit
      WHERE unit.trainingPlan.localId = :planLocalId
      AND unit.dayOfWeek = :dayOfWeek
      """)
  boolean existsInTrainingPlanByDayOfWeek(
      @Param("dayOfWeek") DayOfWeek dayOfWeek,
      @Param("planLocalId") String planLocalId
  );
}
