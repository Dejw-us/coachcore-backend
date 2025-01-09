package pro.shapeit.api.training.set;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.common.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/training-sets")
@RequiredArgsConstructor
public class TrainingSetController {
  private final TrainingSetService trainingSetService;

  private final TrainingSetMapper trainingSetMapper;

  @PatchMapping("/{setLocalId}")
  @Operation(
      summary = "Update training set",
      responses = @ApiResponse(
          description = "Updated training set with specified local id",
          responseCode = "200",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = TrainingSetDto.class)
          )
      ),
      tags = "Training plans"
  )
  public ResponseEntity<?> patchTrainingSet(@PathVariable String setLocalId, @RequestBody UpdateTrainingSetDto dto) throws ResourceNotFoundException {
    var set = trainingSetService.findTrainingSet(setLocalId);
    var updatedSet = trainingSetService.updateTrainingSet(set, dto);
    var updatedSetDto = trainingSetMapper.map(updatedSet);

    return ResponseEntity
        .ok(updatedSetDto);
  }
}
