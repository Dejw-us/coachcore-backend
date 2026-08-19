package pro.coachcore.training.plan.unit;

import java.time.DayOfWeek;
import jakarta.validation.constraints.Size;
import pro.coachcore.validation.annotation.ValidEnum;

public record UpdateTrainingUnitDto(
    @ValidEnum(value = DayOfWeek.class, acceptNull = true) String dayOfWeek,
    @Size(max = 200) String notes, @Size(max = 50, min = 1) String name) {
}
