package pro.shapeit.api.training.unit;

import pro.shapeit.api.training.exercise.TrainingExerciseDto;

import java.time.DayOfWeek;
import java.util.List;

public record TrainingUnitDto(
    String localId,
    String notes,
    DayOfWeek dayOfWeek,
    List<TrainingExerciseDto> exercises
) {
}
