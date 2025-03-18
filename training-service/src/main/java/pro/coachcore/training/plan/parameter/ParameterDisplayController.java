package pro.coachcore.training.plan.parameter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/training-plans/{planId}/units/{unitId}/display")
public class ParameterDisplayController {
  private final ParameterDisplayService parameterDisplayService;
  private final ParameterDisplayMapper parameterDisplayMapper;

  @PatchMapping
  ResponseEntity<ParameterDisplayDto> patchParamaterDisplay(@PathVariable String unitId,
      @RequestBody UpdateParameterDisplayDto dto) {
    var updatedDisplay = parameterDisplayService.updateParameterDisplay(unitId, dto);

    return ResponseEntity.ok(parameterDisplayMapper.map(updatedDisplay));
  }

  @GetMapping
  ResponseEntity<ParameterDisplayDto> getParameterDisplay(@PathVariable String unitId) {
    var display = parameterDisplayService.findByUnit(unitId);

    return ResponseEntity.ok(parameterDisplayMapper.map(display));
  }
}
