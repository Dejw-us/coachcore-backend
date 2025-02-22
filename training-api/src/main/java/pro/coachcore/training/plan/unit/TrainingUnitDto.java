package pro.coachcore.training.plan.unit;

import java.time.DayOfWeek;
import java.util.List;

import pro.coachcore.training.plan.exercise.TrainingExerciseDto;

public record TrainingUnitDto(
    String id,
    String name,
    String notes,
    DayOfWeek dayOfWeek,
    List<TrainingExerciseDto> exercises
) {
}
