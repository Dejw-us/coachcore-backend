package pro.shapeit.training.plan;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateTrainingPlanDto(
    @Size(max = 50, min = 5)
    @NotEmpty
    String name,
    @Size(max = 200, min = 10)
    @NotEmpty
    String description,
    @NotNull
    List<String> goals
) {
}
