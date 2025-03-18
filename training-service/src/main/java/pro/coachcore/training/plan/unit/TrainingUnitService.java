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

/**
 * Service class responsible for handling operations related to training units within a training
 * plan. Provides methods to retrieve, create, update, and delete training units.
 */
@Service
@RequiredArgsConstructor
public class TrainingUnitService {
  private final TrainingUnitRepository trainingUnitRepository;
  private final TrainingPlanRepository trainingPlanRepository;
  private final MessageService messageService;

  /**
   * Retrieves all training units for a given training plan.
   *
   * @param planLocalId the local ID of the training plan.
   * @return a list of training units for the specified plan.
   * @throws ResourceNotFoundException if the training plan is not found.
   */
  public List<TrainingUnit> getAllUnits(String planLocalId) {
    if (!trainingPlanRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.plan.not-found"));
    }
    return trainingUnitRepository.findAllByTrainingPlan_LocalId(planLocalId);
  }

  /**
   * Retrieves a specific training unit by its local ID and the local ID of the training plan.
   *
   * @param planLocalId the local ID of the training plan.
   * @param unitLocalId the local ID of the training unit.
   * @return the training unit with the specified local ID.
   * @throws ResourceNotFoundException if the training unit is not found.
   */
  public TrainingUnit getUnit(String planLocalId, String unitLocalId) {
    return trainingUnitRepository.findByTrainingPlan_LocalIdAndLocalId(planLocalId, unitLocalId)
        .orElseThrow(ResourceNotFoundException
            .supplier(messageService.getMessage("training.unit.not-found")));
  }

  /**
   * Retrieves a specific training unit by the day of the week and the local ID of the training
   * plan.
   *
   * @param planLocalId the local ID of the training plan.
   * @param dayOfWeek the day of the week for the training unit.
   * @return the training unit for the specified day of the week.
   * @throws ResourceNotFoundException if the training unit is not found.
   */
  public TrainingUnit getUnit(String planLocalId, DayOfWeek dayOfWeek) {
    return trainingUnitRepository.findByTrainingPlan_LocalIdAndDayOfWeek(planLocalId, dayOfWeek)
        .orElseThrow(ResourceNotFoundException
            .supplier(messageService.getMessage("training.unit.not-found")));
  }

  /**
   * Saves a new training unit for a given training plan.
   *
   * @param plan the training plan to which the training unit will be added.
   * @param dto the DTO containing the details of the training unit.
   * @return the saved training unit.
   * @throws ResourceAlreadyExistsException if a training unit already exists for the specified day
   *         of the week.
   */
  public TrainingUnit saveUnit(TrainingPlan plan, CreateTrainingUnitDto dto) {
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

  /**
   * Updates an existing training unit.
   *
   * @param unit the training unit to be updated.
   * @param dto the DTO containing the updates for the training unit.
   * @param planLocalId the local ID of the training plan the unit belongs to.
   * @return the updated training unit.
   * @throws ResourceAlreadyExistsException if a training unit already exists for the specified day
   *         of the week.
   */
  public TrainingUnit updateUnit(TrainingUnit unit, UpdateTrainingUnitDto dto, String planLocalId) {
    var dayOfWeek = getEnum(DayOfWeek.class, dto.dayOfWeek());
    if (trainingUnitRepository.existsByTrainingPlan_LocalIdAndDayOfWeek(planLocalId, dayOfWeek)) {
      throw new ResourceAlreadyExistsException(getExistsByDayOfWeekMessage(dayOfWeek));
    }
    updateIfNotNull(dayOfWeek, unit::setDayOfWeek);
    updateIfNotNull(dto.notes(), unit::setNotes);
    updateIfNotNull(dto.name(), unit::setName);

    return trainingUnitRepository.save(unit);
  }

  /**
   * Deletes a specific training unit from a training plan.
   *
   * @param planLocalId the local ID of the training plan.
   * @param unitLocalId the local ID of the training unit to be deleted.
   * @throws ResourceNotFoundException if the training unit is not found.
   */
  @Transactional
  public void deleteUnit(String planLocalId, String unitLocalId) {
    if (!trainingUnitRepository.existsByTrainingPlan_LocalIdAndLocalId(planLocalId, unitLocalId)) {
      throw new ResourceNotFoundException(messageService.getMessage("training.unit.not-found"));
    }
    trainingUnitRepository.deleteByTrainingPlan_LocalIdAndLocalId(planLocalId, unitLocalId);
  }

  /**
   * Generates a message indicating that a training unit already exists for the given day of the
   * week.
   *
   * @param dayOfWeek the day of the week for which the unit already exists.
   * @return a localized message indicating the existence of the unit.
   */
  private String getExistsByDayOfWeekMessage(DayOfWeek dayOfWeek) {
    return messageService.getMessage("training.unit.exists.day-of-week",
        dayOfWeek.getDisplayName(TextStyle.FULL, LocaleContextHolder.getLocale()));
  }
}

