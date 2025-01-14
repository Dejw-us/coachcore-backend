package pro.shapeit.api.training.plan.unit;

import java.time.DayOfWeek;

public record UpdateTrainingUnitDto(DayOfWeek dayOfWeek, String notes) {
}
