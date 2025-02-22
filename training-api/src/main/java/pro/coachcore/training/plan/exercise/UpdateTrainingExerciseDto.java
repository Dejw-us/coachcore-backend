package pro.coachcore.training.plan.exercise;

import jakarta.validation.constraints.Size;

public record UpdateTrainingExerciseDto(
    @Size(max = 200)
    String notes
) {
}
