package pro.shapeit.training.plan.unit;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.dto.MessageDto;
import pro.shapeit.exception.ResourceNotFoundException;
import pro.shapeit.training.plan.TrainingPlanService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/training-plans/{planId}/units")
public class TrainingUnitController {
  private final TrainingPlanService trainingPlanService;
  private final TrainingUnitService trainingUnitService;

  private final TrainingUnitMapper trainingUnitMapper;

  @GetMapping
  ResponseEntity<List<TrainingUnitDto>> getTrainingUnits(
      @PathVariable String planId
  ) throws BadRequestException {
    var units = trainingUnitService.findAllTrainingUnitsByTrainingPlanLocalId(planId);
    var unitsDto = trainingUnitMapper.map(units);
    return ResponseEntity
        .ok(unitsDto);
  }

  @PostMapping
  ResponseEntity<TrainingUnitDto> postTrainingUnit(
      @PathVariable String planId,
      @RequestBody @Valid CreateTrainingUnitDto dto
  ) throws ResourceNotFoundException, BadRequestException {
    var plan = trainingPlanService.findTrainingPlanByLocalId(planId);
    var savedUnit = trainingUnitService.saveTrainingUnit(plan, dto);
    var savedUnitDto = trainingUnitMapper.map(savedUnit);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedUnitDto);
  }

  @PatchMapping("/{unitId}")
  ResponseEntity<TrainingUnitDto> patchTrainingUnit(
      @PathVariable String planId,
      @PathVariable String unitId,
      @RequestBody @Valid UpdateTrainingUnitDto dto
  ) throws ResourceNotFoundException {
    var unit = trainingUnitService.findTrainingUnitByTrainingPlanLocalIdAndLocalId(planId, unitId);
    var updatedUnit = trainingUnitService.updateTrainingUnit(unit, dto, planId);
    var updatedUnitDto = trainingUnitMapper.map(updatedUnit);

    return ResponseEntity
        .ok(updatedUnitDto);
  }

  @DeleteMapping("/{unitId}")
  ResponseEntity<MessageDto> deleteTrainingUnit(
      @PathVariable String planId,
      @PathVariable String unitId
  ) {
    trainingUnitService.deleteTrainingUnitByTrainingPlanLocalIdAndLocalId(planId, unitId);

    return ResponseEntity
        .ok(new MessageDto("Training unit has been deleted"));
  }
}
