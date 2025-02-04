package pro.shapeit.training.plan.unit;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import pro.shapeit.validation.annotation.ValidEnum;

import java.time.DayOfWeek;

public record CreateTrainingUnitDto(
    @ValidEnum(DayOfWeek.class)
    String dayOfWeek,
    @Size(max = 200)
    String notes,
    @Size(max = 50, min = 1)
    @NotEmpty
    String name
) {
}
