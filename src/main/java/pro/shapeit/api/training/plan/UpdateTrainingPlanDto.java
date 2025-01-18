package pro.shapeit.api.training.plan;

import jakarta.validation.constraints.Max;

public record UpdateTrainingPlanDto(
    @Max(50L)
    String name,
    @Max(200L)
    String description
) {
}
