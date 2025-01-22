package pro.shapeit.training.plan.unit;

import pro.shapeit.backend.validation.annotation.ValidEnum;

import java.time.DayOfWeek;

public record CreateTrainingUnitDto(
    @ValidEnum(DayOfWeek.class)
    String dayOfWeek
) {
}
