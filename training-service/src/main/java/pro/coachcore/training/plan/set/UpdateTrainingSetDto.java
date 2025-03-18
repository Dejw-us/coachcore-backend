package pro.coachcore.training.plan.set;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record UpdateTrainingSetDto(@Min(1L) @Max(10000L) Integer reps,
    @DecimalMin("1.0") @DecimalMax("10.0") Double intensity,
    @Pattern(regexp = "^([0-9x]-){3}[0-9x]$",
        message = "Rate must match the pattern '1-1-1-1' where each part is a number or 'x'") String rate,
    @DecimalMin("0.0") @DecimalMax("3600.0") Double restSeconds,
    @DecimalMin("0.0") @DecimalMax("10000.0") Double weight) {
}
