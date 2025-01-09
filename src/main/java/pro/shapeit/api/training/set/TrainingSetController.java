package pro.shapeit.api.training.set;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.common.dto.MessageDto;
import pro.shapeit.api.common.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/training-sets")
@RequiredArgsConstructor
public class TrainingSetController {
  private final TrainingSetService trainingSetService;

  private final TrainingSetMapper trainingSetMapper;

  @PatchMapping("/{setLocalId}")
  @Operation(
      summary = "Update training set"
  )
  public ResponseEntity<?> patchTrainingSet(@PathVariable String setLocalId, @RequestBody UpdateTrainingSetDto dto) throws ResourceNotFoundException {
    var set = trainingSetService.findTrainingSet(setLocalId);
    var updatedSet = trainingSetService.updateTrainingSet(set, dto);

    if (updatedSet == null) {
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(new MessageDto("Failed to update training set"));
    }

    var updatedSetDto = trainingSetMapper.map(updatedSet);

    return ResponseEntity
        .ok(updatedSetDto);
  }
}
