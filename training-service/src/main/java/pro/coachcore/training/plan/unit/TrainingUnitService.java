package pro.coachcore.training.plan.unit;

import static org.apache.commons.lang3.EnumUtils.getEnum;
import static pro.coachcore.util.ServiceUtils.updateIfNotNull;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;
import pro.coachcore.training.plan.TrainingPlan;
import pro.coachcore.training.plan.TrainingPlanRepository;
import pro.coachcore.training.plan.parameter.ParameterDisplay;

@Service
@RequiredArgsConstructor
public class TrainingUnitService {
  private final TrainingUnitRepository trainingUnitRepository;
  private final TrainingPlanRepository trainingPlanRepository;
  private final MessageService messageService;

  public List<TrainingUnit> findAllTrainingUnitsByTrainingPlanLocalId(String planLocalId) {
    if (!trainingPlanRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.plan.not-found"));
    }
    return trainingUnitRepository.findAllByTrainingPlan_LocalId(planLocalId);
  }

  public TrainingUnit findTrainingUnitByTrainingPlanLocalIdAndLocalId(String planLocalId,
      String unitLocalId) {
    return trainingUnitRepository.findByTrainingPlan_LocalIdAndLocalId(planLocalId, unitLocalId)
        .orElseThrow(ResourceNotFoundException
            .supplier(messageService.getMessage("training.unit.not-found")));
  }

  public TrainingUnit findTrainingUnitByTrainingPlanLocalIdAndDayOfWeek(String planLocalId,
      DayOfWeek dayOfWeek) {
    return trainingUnitRepository.findByTrainingPlan_LocalIdAndDayOfWeek(planLocalId, dayOfWeek)
        .orElseThrow(ResourceNotFoundException
            .supplier(messageService.getMessage("training.unit.not-found")));
  }

  public TrainingUnit saveTrainingUnit(TrainingPlan plan, CreateTrainingUnitDto dto) {
    var dayOfWeek = getEnum(DayOfWeek.class, dto.dayOfWeek());

    if (trainingUnitRepository.existsByTrainingPlanAndDayOfWeek(plan, dayOfWeek)) {
      throw new ResourceAlreadyExistsException(getExistsByDayOfWeekMessage(dayOfWeek));
    }

    var unit = new TrainingUnit();

    unit.setName(dto.name());
    unit.setNotes(dto.notes());
    unit.setDayOfWeek(dayOfWeek);
    unit.setTrainingPlan(plan);
    unit.setParameterDisplay(new ParameterDisplay());

    var savedUnit = trainingUnitRepository.save(unit);

    return savedUnit;
  }

  public TrainingUnit updateTrainingUnit(TrainingUnit unit, UpdateTrainingUnitDto dto,
      String planLocalId) {
    var dayOfWeek = getEnum(DayOfWeek.class, dto.dayOfWeek());
    if (trainingUnitRepository.existsByTrainingPlan_LocalIdAndDayOfWeek(planLocalId, dayOfWeek)) {
      throw new ResourceAlreadyExistsException(getExistsByDayOfWeekMessage(dayOfWeek));
    }
    updateIfNotNull(dayOfWeek, unit::setDayOfWeek);
    updateIfNotNull(dto.notes(), unit::setNotes);
    updateIfNotNull(dto.name(), unit::setName);

    return trainingUnitRepository.save(unit);
  }

  @Transactional
  public void deleteTrainingUnitByTrainingPlanLocalIdAndLocalId(String planLocalId,
      String unitLocalId) {
    if (!trainingUnitRepository.existsByTrainingPlan_LocalIdAndLocalId(planLocalId, unitLocalId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.unit.not-found"));
    }
    trainingUnitRepository.deleteByTrainingPlan_LocalIdAndLocalId(planLocalId, unitLocalId);
  }

  private String getExistsByDayOfWeekMessage(DayOfWeek dayOfWeek) {
    return messageService.getMessage("training.unit.exists.day-of-week",
        dayOfWeek.getDisplayName(TextStyle.FULL, LocaleContextHolder.getLocale()));
  }
}
