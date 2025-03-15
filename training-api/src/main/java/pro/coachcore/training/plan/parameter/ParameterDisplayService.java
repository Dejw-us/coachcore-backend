package pro.coachcore.training.plan.parameter;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class ParameterDisplayService {
  private final ParameterDisplayRepository parameterDisplayRepository;

  public ParameterDisplay updateParameterDisplay(String unitLocalId, UpdateParameterDisplayDto dto) {
    var display = findByUnit(unitLocalId);
    display.updateDisplay(dto.updater(), dto.display());
    return parameterDisplayRepository.save(display);
  }

  public ParameterDisplay findByUnit(String unitLocalId) {
    return parameterDisplayRepository.findByTrainingUnit_LocalId(unitLocalId)
        .orElseThrow(ResourceNotFoundException.supplier("Parameter display does not exist"));
  }
}
