package pro.shapeit.training.plan.unit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.backend.common.exception.resource.ResourceAlreadyExistsException;
import pro.shapeit.backend.common.exception.resource.ResourceFailedToUpdateException;
import pro.shapeit.backend.common.exception.resource.ResourceNotFoundException;
import pro.shapeit.training.plan.TrainingPlan;
import pro.shapeit.training.plan.TrainingPlanRepository;

import java.time.DayOfWeek;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.EnumUtils.getEnum;
import static pro.shapeit.backend.common.util.ServiceUtils.updateIfNotNull;

@Service
@RequiredArgsConstructor
public class TrainingUnitService {
  private final TrainingUnitRepository trainingUnitRepository;
  private final TrainingPlanRepository trainingPlanRepository;

  public List<TrainingUnit> findAllTrainingUnitsByTrainingPlanLocalId(
      String planLocalId
  ) throws ResourceNotFoundException {
    if (!trainingPlanRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException("Training pro.shapeit.plan does not exist");
    }
    return trainingUnitRepository.findAllByTrainingPlan_LocalId(planLocalId);
  }

  public TrainingUnit findTrainingUnitByTrainingPlanLocalIdAndLocalId(
      String planLocalId,
      String unitLocalId
  ) throws ResourceNotFoundException {
    return trainingUnitRepository.findByTrainingPlan_LocalIdAndLocalId(planLocalId, unitLocalId)
        .orElseThrow(ResourceNotFoundException.supplier("Training unit does not exist"));
  }

  public TrainingUnit saveTrainingUnit(
      TrainingPlan plan,
      CreateTrainingUnitDto dto
  ) throws ResourceAlreadyExistsException {
    var dayOfWeek = getEnum(DayOfWeek.class, dto.dayOfWeek());
    if (trainingUnitRepository.existsByTrainingPlanAndDayOfWeek(plan, dayOfWeek)) {
      throw new ResourceAlreadyExistsException(format("Training unit for %s already exist", dto.dayOfWeek()));
    }
    var unit = new TrainingUnit();
    unit.setDayOfWeek(dayOfWeek);
    unit.setTrainingPlan(plan);

    return trainingUnitRepository.save(unit);
  }

  public TrainingUnit updateTrainingUnit(
      TrainingUnit unit,
      UpdateTrainingUnitDto dto,
      String planLocalId
  ) throws ResourceFailedToUpdateException {
    var dayOfWeek = getEnum(DayOfWeek.class, dto.dayOfWeek());
    if (trainingUnitRepository.existsInTrainingPlanByDayOfWeek(dayOfWeek, planLocalId)) {
      throw new ResourceFailedToUpdateException(format("Training unit for %s already exists", dayOfWeek));
    }
    updateIfNotNull(dayOfWeek, unit::setDayOfWeek);
    updateIfNotNull(dto.notes(), unit::setNotes);
    updateIfNotNull(dto.name(), unit::setName);

    return trainingUnitRepository.save(unit);
  }

  public boolean deleteTrainingUnitByTrainingPlanLocalIdAndLocalId(String planLocalId, String unitLocalId) {
    return trainingUnitRepository.deleteByTrainingPlan_LocalIdAndLocalIdWithCount(planLocalId, unitLocalId) > 0;
  }
}
