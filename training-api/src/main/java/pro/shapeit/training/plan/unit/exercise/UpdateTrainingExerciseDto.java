package pro.shapeit.training.plan.unit.exercise;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateTrainingExerciseDto(
    @Size(max = 200)
    @NotEmpty
    String notes
) {
}
