package pro.shapeit.training.plan.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.shapeit.training.plan.TrainingPlan;
import pro.shapeit.training.plan.TrainingPlanRepository;

import java.time.DayOfWeek;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingUnitServiceTests {
  @Mock
  private TrainingPlanRepository trainingPlanRepository;

  @Mock
  private TrainingUnitRepository trainingUnitRepository;

  @InjectMocks
  private TrainingUnitService trainingUnitService;

  @Test
  void throwsResourceNotFound_findAllTrainingUnitsByTrainingPlanLocalId() {
    var planId = UUID.randomUUID().toString();

    when(trainingPlanRepository.existsByLocalId(planId)).thenReturn(false);

    assertThrows(
        ResourceNotFoundException.class,
        () -> trainingUnitService.findAllTrainingUnitsByTrainingPlanLocalId(planId)
    );
  }

  @Test
  void throwsResourceAlreadyExists_saveTrainingUnit() {
    var dto = new CreateTrainingUnitDto("MONDAY", "some notes", "name");
    var plan = new TrainingPlan();

    when(trainingUnitRepository.existsByTrainingPlanAndDayOfWeek(plan, DayOfWeek.MONDAY)).thenReturn(true);

    assertThrows(
        ResourceAlreadyExistsException.class,
        () -> trainingUnitService.saveTrainingUnit(plan, dto)
    );
  }

  @Test
  void throwsResourceAlreadyExists_updateTrainingUnit() {
    var unit = new TrainingUnit();
    var dto = new UpdateTrainingUnitDto("MONDAY", "Notes", "Name");
    var planId = UUID.randomUUID().toString();

    when(trainingUnitRepository.existsInTrainingPlanByDayOfWeek(DayOfWeek.MONDAY, planId)).thenReturn(true);

    assertThrows(
        ResourceAlreadyExistsException.class,
        () -> trainingUnitService.updateTrainingUnit(unit, dto, planId)
    );
  }

  @Test
  void throwsResourceNotFound_deleteTrainingUnitByTrainingPlanLocalIdAndLocalId() {
    var planId = UUID.randomUUID().toString();
    var unitId = UUID.randomUUID().toString();

    when(trainingUnitRepository.existsByTrainingPlan_LocalIdAndLocalId(planId, unitId)).thenReturn(false);

    assertThrows(
        ResourceNotFoundException.class,
        () -> trainingUnitService.deleteTrainingUnitByTrainingPlanLocalIdAndLocalId(planId, unitId)
    );
  }
}