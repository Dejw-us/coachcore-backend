package pro.coachcore.training.plan.unit;

import java.time.DayOfWeek;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/public/training-plans/{planId}/units")
public class PublicTrainingUnitController {
  private final TrainingUnitService trainingUnitService;
  private final TrainingUnitMapper trainingUnitMapper;

  @GetMapping
  ResponseEntity<?> getTrainingUnits(@PathVariable String planId,
      @RequestParam(required = false) String dayOfWeek,
      @RequestParam(required = false) Boolean preview) {
    if (dayOfWeek != null) {
      var unit = trainingUnitService.getUnit(planId, EnumUtils.getEnum(DayOfWeek.class, dayOfWeek));
      var unitDto = trainingUnitMapper.map(unit, preview);

      return ResponseEntity.ok(unitDto);
    }

    var units = trainingUnitService.getAllUnits(planId);
    var unitsDto = trainingUnitMapper.map(units, preview);
    return ResponseEntity.ok(unitsDto);
  }
}
