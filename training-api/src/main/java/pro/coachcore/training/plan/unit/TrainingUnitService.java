package pro.coachcore.training.plan.unit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.training.plan.TrainingPlan;
import pro.coachcore.training.plan.TrainingPlanRepository;
import pro.coachcore.training.plan.parameter.ParameterDisplay;
import pro.coachcore.training.plan.parameter.ParameterDisplayRepository;

import java.time.DayOfWeek;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.EnumUtils.getEnum;
import static pro.coachcore.util.ServiceUtils.updateIfNotNull;

@Service
@RequiredArgsConstructor
public class TrainingUnitService {
  private final TrainingUnitRepository trainingUnitRepository;
  private final TrainingPlanRepository trainingPlanRepository;
  private final ParameterDisplayRepository parameterDisplayRepository;

  public List<TrainingUnit> findAllTrainingUnitsByTrainingPlanLocalId(String planLocalId) {
    if (!trainingPlanRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException("Training plan does not exist");
    }
    return trainingUnitRepository.findAllByTrainingPlan_LocalId(planLocalId);
  }

  public TrainingUnit findTrainingUnitByTrainingPlanLocalIdAndLocalId(String planLocalId, String unitLocalId) {
    return trainingUnitRepository.findByTrainingPlan_LocalIdAndLocalId(planLocalId, unitLocalId)
        .orElseThrow(ResourceNotFoundException.supplier("Training unit does not exist"));
  }

  public TrainingUnit findTrainingUnitByTrainingPlanLocalIdAndDayOfWeek(String planLocalId, DayOfWeek dayOfWeek) {
    return trainingUnitRepository.findByTrainingPlan_LocalIdAndDayOfWeek(planLocalId, dayOfWeek)
        .orElseThrow(ResourceNotFoundException.supplier("Training unit does not exist"));
  }

  public TrainingUnit saveTrainingUnit(TrainingPlan plan, CreateTrainingUnitDto dto) {
    var dayOfWeek = getEnum(DayOfWeek.class, dto.dayOfWeek());

    if (trainingUnitRepository.existsByTrainingPlanAndDayOfWeek(plan, dayOfWeek)) {
      throw new ResourceAlreadyExistsException(format("Training unit for %s already exists", dayOfWeek));
    }

    var unit = new TrainingUnit();

    unit.setName(dto.name());
    unit.setNotes(dto.notes());
    unit.setDayOfWeek(dayOfWeek);
    unit.setTrainingPlan(plan);

    var display = new ParameterDisplay();
    var savedUnit = trainingUnitRepository.save(unit);

    display.setTrainingUnit(savedUnit);
    parameterDisplayRepository.save(display);

    return savedUnit;
  }

  public TrainingUnit updateTrainingUnit(TrainingUnit unit, UpdateTrainingUnitDto dto, String planLocalId) {
    var dayOfWeek = getEnum(DayOfWeek.class, dto.dayOfWeek());
    if (trainingUnitRepository.existsByTrainingPlan_LocalIdAndDayOfWeek(planLocalId, dayOfWeek)) {
      throw new ResourceAlreadyExistsException(format("Training unit for %s already exists", dayOfWeek));
    }
    updateIfNotNull(dayOfWeek, unit::setDayOfWeek);
    updateIfNotNull(dto.notes(), unit::setNotes);
    updateIfNotNull(dto.name(), unit::setName);

    return trainingUnitRepository.save(unit);
  }

  public void deleteTrainingUnitByTrainingPlanLocalIdAndLocalId(String planLocalId, String unitLocalId) {
    if (!trainingUnitRepository.existsByTrainingPlan_LocalIdAndLocalId(planLocalId, unitLocalId)) {
      throw new ResourceNotFoundException("Training unit does not exist");
    }
    trainingUnitRepository.deleteByTrainingPlan_LocalIdAndLocalId(planLocalId, unitLocalId);
  }
}
