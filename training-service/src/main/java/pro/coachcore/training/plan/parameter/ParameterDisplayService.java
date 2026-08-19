package pro.coachcore.training.plan.parameter;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;
import pro.coachcore.training.plan.unit.TrainingUnit;
import pro.coachcore.training.plan.unit.TrainingUnitRepository;

/**
 * Service class responsible for handling operations related to the display parameters of training
 * units. Provides methods to update and retrieve the display parameters for a training unit.
 */
@Service
@RequiredArgsConstructor
public class ParameterDisplayService {
  private final TrainingUnitRepository unitRepository;
  private final MessageService messageService;

  /**
   * Updates the display parameters of a specific training unit.
   *
   * @param unitLocalId the local ID of the training unit.
   * @param dto the DTO containing the updated display parameters.
   * @return the updated ParameterDisplay object of the training unit.
   * @throws ResourceNotFoundException if the training unit is not found.
   */
  public ParameterDisplay updateParameterDisplay(String unitLocalId,
      UpdateParameterDisplayDto dto) {
    var unit = unitRepository.findByLocalId(unitLocalId).orElseThrow(
        ResourceNotFoundException.supplier(messageService.getMessage("training.unit.not-found")));

    unit.getParameterDisplay().updateDisplay(dto.updater(), dto.display());
    var savedUnit = unitRepository.save(unit);
    return savedUnit.getParameterDisplay();
  }

  /**
   * Retrieves the display parameters for a specific training unit.
   *
   * @param unitLocalId the local ID of the training unit.
   * @return the ParameterDisplay object for the specified training unit.
   * @throws ResourceNotFoundException if the training unit is not found.
   */
  public ParameterDisplay findByUnit(String unitLocalId) {
    return unitRepository.findByLocalId(unitLocalId).map(TrainingUnit::getParameterDisplay)
        .orElseThrow(ResourceNotFoundException
            .supplier(messageService.getMessage("training.unit.not-found")));
  }
}

