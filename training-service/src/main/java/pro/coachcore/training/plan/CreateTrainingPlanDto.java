package pro.coachcore.training.plan;

import java.util.List;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTrainingPlanDto(@Size(max = 50, min = 5) @NotEmpty String name,
    @Size(max = 200, min = 10) @NotEmpty String description, @NotNull List<String> goals,
    @Max(10) @Min(1) Integer weeks) {
}
