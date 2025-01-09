package pro.shapeit.api.training.plan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.goal.TrainingGoalDto;
import pro.shapeit.api.training.goal.TrainingGoalMapper;
import pro.shapeit.api.training.unit.CreateTrainingUnitDto;
import pro.shapeit.api.training.unit.TrainingUnitDto;
import pro.shapeit.api.training.unit.TrainingUnitMapper;
import pro.shapeit.api.training.unit.TrainingUnitService;

@RestController
@RequestMapping("/training-plans")
@RequiredArgsConstructor
public class TrainingPlanController {
  private final TrainingPlanService trainingPlanService;
  private final TrainingUnitService trainingUnitService;

  private final TrainingUnitMapper trainingUnitMapper;
  private final TrainingPlanMapper trainingPlanMapper;
  private final TrainingGoalMapper trainingGoalMapper;

  @GetMapping("/{planLocalId}/goals")
  @Operation(
      summary = "Get all goals of training plan",
      responses = @ApiResponse(
          description = "List of goals for the specified training plan",
          responseCode = "200",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              array = @ArraySchema(
                  schema = @Schema(implementation = TrainingGoalDto.class)
              )
          )
      ),
      tags = "Training plans"
  )
  public ResponseEntity<?> getTrainingGoal(@PathVariable String planLocalId) {
    var goals = trainingPlanService.findTrainingGoals(planLocalId);
    var goalsDto = goals.stream()
        .map(trainingGoalMapper::map)
        .toList();

    return ResponseEntity
        .ok(goalsDto);
  }

  @GetMapping
  @Operation(
      summary = "Get first 10 training plans",
      responses = @ApiResponse(
          description = "List of training plans",
          responseCode = "200",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              array = @ArraySchema(
                  schema = @Schema(implementation = TrainingPlanDto.class)
              )
          )
      ),
      tags = "Training plans"
  )
  public ResponseEntity<?> getTrainingPlans() {
    var plans = trainingPlanService.findTrainingPlans(10);
    var plansDto = plans
        .map(trainingPlanMapper::map)
        .toList();

    return ResponseEntity
        .ok(plansDto);
  }

  @GetMapping("/{planLocalId}")
  @Operation(
      summary = "Get specific training plan",
      responses = @ApiResponse(
          description = "Training plan with specified local id",
          responseCode = "200",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = TrainingPlanDto.class)
          )
      ),
      tags = "Training plans"
  )
  public ResponseEntity<?> getTrainingPlan(@PathVariable String planLocalId) throws ResourceNotFoundException {
    var plan = trainingPlanService.findTrainingPlan(planLocalId);
    var planDto = trainingPlanMapper.map(plan);

    return ResponseEntity
        .ok(planDto);
  }

  @GetMapping("/{planLocalId}/units")
  @Operation(
      summary = "Get all units of training plan",
      responses = @ApiResponse(
          description = "List units related to specified training plan",
          responseCode = "200",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              array = @ArraySchema(
                  schema = @Schema(implementation = TrainingUnitDto.class)
              )
          )
      ),
      tags = "Training plans"
  )
  public ResponseEntity<?> getTrainingPlanUnits(@PathVariable String planLocalId) throws ResourceNotFoundException {
    var units = trainingUnitService.findTrainingUnits(planLocalId);
    var unitsDto = units.stream()
        .map(trainingUnitMapper::map)
        .toList();

    return ResponseEntity.ok(unitsDto);
  }

  @PostMapping("/{planLocalId}/units")
  @Operation(
      summary = "Save new unit on training plan",
      responses = @ApiResponse(
          description = "Saved training unit",
          responseCode = "201",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = TrainingUnitDto.class)
          )
      ),
      tags = "Training plans"
  )
  public ResponseEntity<?> postTrainingPlanUnit(
      @PathVariable String planLocalId,
      @RequestBody CreateTrainingUnitDto dto
  ) throws ResourceNotFoundException {
    var plan = trainingPlanService.findTrainingPlan(planLocalId);
    var savedUnit = trainingUnitService.saveTrainingUnit(dto, plan);
    var savedUnitDto = trainingUnitMapper.map(savedUnit);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedUnitDto);
  }

  @PostMapping
  @Operation(
      summary = "Save new training plan",
      responses = @ApiResponse(
          description = "Saved training plan",
          responseCode = "201",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = TrainingPlanDto.class)
          )
      ),
      tags = "Training plans"
  )
  public ResponseEntity<?> postTrainingPlan(@RequestBody CreateTrainingPlanDto dto) {
    var savedPlan = trainingPlanService.saveTrainingPlan(dto);
    var savedPlanDto = trainingPlanMapper.map(savedPlan);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedPlanDto);
  }
}
