package pro.shapeit.backend.training.plan.unit;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import pro.shapeit.backend.validation.annotation.ValidEnum;

import java.time.DayOfWeek;

public record UpdateTrainingUnitDto(
    @ValidEnum(DayOfWeek.class)
    String dayOfWeek,
    @Size(max = 200)
    @NotEmpty
    String notes,
    @Size(max = 50)
    @NotEmpty
    String name
) {
}
