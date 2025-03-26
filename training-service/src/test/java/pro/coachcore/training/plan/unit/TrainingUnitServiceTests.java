package pro.coachcore.training.plan.unit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;
import pro.coachcore.training.TestDtoFactory;
import pro.coachcore.training.plan.TrainingPlan;
import pro.coachcore.training.plan.TrainingPlanRepository;

@ExtendWith(MockitoExtension.class)
class TrainingUnitServiceTests {
  @Mock
  private TrainingPlanRepository trainingPlanRepository;

  @Mock
  private TrainingUnitRepository trainingUnitRepository;

  @Mock
  private MessageService messageService;

  @InjectMocks
  private TrainingUnitService trainingUnitService;

  @Test
  void getAllUnits_shouldThrowException_whenParentPlanNotFound() {
    var planId = "plan";

    when(trainingPlanRepository.existsByLocalId(planId)).thenReturn(false);

    assertThrows(ResourceNotFoundException.class, () -> trainingUnitService.getAllUnits(planId));
  }

  @Test
  void saveUnit_shouldThrowException_whenAlreadyExists() {
    var plan = spy(new TrainingPlan());
    var dto = TestDtoFactory.createUnitDto();

    when(plan.getLocalId()).thenReturn("plan");
    when(plan.getWeeks()).thenReturn(2);
    when(trainingUnitRepository.countByTrainingPlan_LocalIdAndDayOfWeek(plan.getLocalId(),
        DayOfWeek.valueOf(dto.dayOfWeek())))
        .thenReturn(2L);

    assertThrows(ResourceAlreadyExistsException.class,
        () -> trainingUnitService.saveUnit(plan, dto));
  }

  @Test
  void updateUnit_shouldThrowException_whenUnitNotFound() {
    var unit = new TrainingUnit();
    var dto = TestDtoFactory.updateUnitDto();
    var planId = "plan";

    when(trainingUnitRepository.existsByTrainingPlan_LocalIdAndDayOfWeek(planId, DayOfWeek.valueOf(dto.dayOfWeek())))
        .thenReturn(true);

    assertThrows(ResourceAlreadyExistsException.class,
        () -> trainingUnitService.updateUnit(unit, dto, planId));
  }

  @Test
  void deleteUnit_shouldThrowException_whenUnitNotFound() {
    var planId = "plan";
    var unitId = "unit";

    when(trainingUnitRepository.existsByTrainingPlan_LocalIdAndLocalId(planId, unitId)).thenReturn(false);

    assertThrows(ResourceNotFoundException.class, () -> trainingUnitService.deleteUnit(planId, unitId));
  }
}
