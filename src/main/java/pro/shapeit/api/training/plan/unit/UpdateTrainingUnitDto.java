package pro.shapeit.api.training.plan.unit;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import pro.shapeit.api.validation.annotation.ValidDayOfWeek;

public record UpdateTrainingUnitDto(
    @ValidDayOfWeek
    String dayOfWeek,
    @Size(max = 200)
    @NotEmpty
    String notes,
    @Size(max = 50)
    @NotEmpty
    String name
) {
}
