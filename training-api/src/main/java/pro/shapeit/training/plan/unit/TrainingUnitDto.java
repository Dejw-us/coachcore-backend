package pro.shapeit.training.plan.unit;

import pro.shapeit.training.plan.unit.exercise.TrainingExerciseDto;

import java.time.DayOfWeek;
import java.util.List;

public record TrainingUnitDto(
    String id,
    String notes,
    DayOfWeek dayOfWeek,
    List<TrainingExerciseDto> exercises
) {
}
