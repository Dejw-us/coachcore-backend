package pro.coachcore.training.plan;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTrainingPlanDto(@Size(max = 50, min = 5) @NotEmpty String name,
    @Size(max = 200, min = 10) @NotEmpty String description, @NotNull List<String> goals,
    @Size(min = 1, max = 10) Integer weeks) {
}
