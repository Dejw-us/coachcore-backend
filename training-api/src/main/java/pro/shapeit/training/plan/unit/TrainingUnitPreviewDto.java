package pro.shapeit.training.plan.unit;

import java.time.DayOfWeek;

public record TrainingUnitPreviewDto(
    String id,
    String name,
    DayOfWeek dayOfWeek
) {
}
