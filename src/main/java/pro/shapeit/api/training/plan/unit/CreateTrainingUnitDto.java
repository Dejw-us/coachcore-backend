package pro.shapeit.api.training.plan.unit;

import pro.shapeit.api.validation.annotation.ValidEnum;

import java.time.DayOfWeek;

public record CreateTrainingUnitDto(
    @ValidEnum(DayOfWeek.class)
    String dayOfWeek
) {
}
