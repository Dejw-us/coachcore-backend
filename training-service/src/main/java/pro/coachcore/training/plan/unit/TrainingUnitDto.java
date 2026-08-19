package pro.coachcore.training.plan.unit;

import java.time.DayOfWeek;

public record TrainingUnitDto(String id, String name, String notes, Integer index,
    DayOfWeek dayOfWeek) {
}
