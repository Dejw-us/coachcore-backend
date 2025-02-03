package pro.shapeit.training.plan.unit;

import jakarta.validation.constraints.Size;
import pro.shapeit.validation.annotation.ValidEnum;

import java.time.DayOfWeek;

public record UpdateTrainingUnitDto(
    @ValidEnum(value = DayOfWeek.class, acceptNull = true)
    String dayOfWeek,
    @Size(max = 200)
    String notes,
    @Size(max = 50)
    String name
) {
}
