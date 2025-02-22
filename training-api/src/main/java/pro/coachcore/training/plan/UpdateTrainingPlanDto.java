package pro.coachcore.training.plan;

import jakarta.validation.constraints.Size;

public record UpdateTrainingPlanDto(
    @Size(max = 50, min = 5)
    String name,
    @Size(max = 200, min = 10)
    String description
) {
}
