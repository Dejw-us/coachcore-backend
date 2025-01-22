package pro.shapeit.backend.training.plan.unit;

import pro.shapeit.backend.training.plan.unit.exercise.TrainingExerciseDto;

import java.time.DayOfWeek;
import java.util.List;

public record TrainingUnitDto(
    String localId,
    String notes,
    DayOfWeek dayOfWeek,
    List<TrainingExerciseDto> exercises
) {
}
