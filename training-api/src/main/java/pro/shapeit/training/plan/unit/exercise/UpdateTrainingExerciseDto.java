package pro.shapeit.training.plan.unit.exercise;

import jakarta.validation.constraints.Size;

public record UpdateTrainingExerciseDto(
    @Size(max = 200)
    String notes
) {
}
