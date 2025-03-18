package pro.coachcore.training.plan.parameter;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;
import pro.coachcore.training.plan.unit.TrainingUnit;
import pro.coachcore.training.plan.unit.TrainingUnitRepository;

@Service
@RequiredArgsConstructor
public class ParameterDisplayService {
  private final TrainingUnitRepository unitRepository;
  private final MessageService messageService;

  public ParameterDisplay updateParameterDisplay(String unitLocalId,
      UpdateParameterDisplayDto dto) {
    var unit = unitRepository.findByLocalId(unitLocalId).orElseThrow(
        ResourceNotFoundException.supplier(messageService.getMessage("training.unit.not-found")));

    unit.getParameterDisplay().updateDisplay(dto.updater(), dto.display());
    var savedUnit = unitRepository.save(unit);
    return savedUnit.getParameterDisplay();
  }

  public ParameterDisplay findByUnit(String unitLocalId) {
    return unitRepository.findByLocalId(unitLocalId).map(TrainingUnit::getParameterDisplay)
        .orElseThrow(ResourceNotFoundException
            .supplier(messageService.getMessage("training.unit.not-found")));
  }
}
