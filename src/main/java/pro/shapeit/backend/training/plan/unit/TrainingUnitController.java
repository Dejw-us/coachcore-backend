package pro.shapeit.backend.training.plan.unit;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.backend.common.dto.MessageDto;
import pro.shapeit.backend.common.exception.resource.ResourceAlreadyExistsException;
import pro.shapeit.backend.common.exception.resource.ResourceFailedToUpdateException;
import pro.shapeit.backend.common.exception.resource.ResourceNotFoundException;
import pro.shapeit.backend.training.plan.TrainingPlanService;

import java.util.List;

import static pro.shapeit.backend.common.util.ControllerUtils.deleteResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/training-plans/{planId}/units")
public class TrainingUnitController {
  private final TrainingPlanService trainingPlanService;
  private final TrainingUnitService trainingUnitService;

  private final TrainingUnitMapper trainingUnitMapper;

  // --- GET ---

  @GetMapping
  ResponseEntity<List<TrainingUnitDto>> getTrainingUnits(
      @PathVariable String planId
  ) throws ResourceNotFoundException {
    var units = trainingUnitService.findAllTrainingUnitsByTrainingPlanLocalId(planId);
    var unitsDto = trainingUnitMapper.map(units);

    return ResponseEntity
        .ok(unitsDto);
  }

  // --- POST ---

  @PostMapping
  ResponseEntity<TrainingUnitDto> postTrainingUnit(
      @PathVariable String planId,
      @RequestBody @Valid CreateTrainingUnitDto dto
  ) throws ResourceNotFoundException, ResourceAlreadyExistsException {
    var plan = trainingPlanService.findTrainingPlanByLocalId(planId);
    var savedUnit = trainingUnitService.saveTrainingUnit(plan, dto);
    var savedUnitDto = trainingUnitMapper.map(savedUnit);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedUnitDto);
  }

  // --- PATCH ---

  @PatchMapping("/{unitId}")
  ResponseEntity<TrainingUnitDto> patchTrainingUnit(
      @PathVariable String planId,
      @PathVariable String unitId,
      @RequestBody UpdateTrainingUnitDto dto
  ) throws ResourceNotFoundException, ResourceFailedToUpdateException {
    var unit = trainingUnitService.findTrainingUnitByTrainingPlanLocalIdAndLocalId(planId, unitId);
    var updatedUnit = trainingUnitService.updateTrainingUnit(unit, dto, planId);
    var updatedUnitDto = trainingUnitMapper.map(updatedUnit);

    return ResponseEntity
        .ok(updatedUnitDto);
  }

  // --- DELETE ---

  @DeleteMapping("/{unitId}")
  ResponseEntity<MessageDto> deleteTrainingUnit(
      @PathVariable String planId,
      @PathVariable String unitId
  ) {
    var isDeleted = trainingUnitService.deleteTrainingUnitByTrainingPlanLocalIdAndLocalId(planId, unitId);

    return deleteResponse(isDeleted, "training unit");
  }
}
