package pro.shapeit.api.training.plan;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateTrainingPlanDto(
    @Max(50L)
    @NotEmpty
    String name,
    @Max(200L)
    @NotEmpty
    String description,
    @NotNull
    List<String> goals
) {
}
