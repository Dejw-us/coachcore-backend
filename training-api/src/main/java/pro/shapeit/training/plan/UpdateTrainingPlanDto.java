package pro.shapeit.training.plan;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateTrainingPlanDto(
    @Size(max = 50, min = 5)
    @NotEmpty
    String name,
    @Size(max = 200, min = 10)
    @NotEmpty
    String description
) {
}
