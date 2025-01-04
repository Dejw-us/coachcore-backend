package pro.shapeit.api.training.exercise;

import pro.shapeit.api.training.set.TrainingSetDto;

import java.util.List;

public record TrainingExerciseDto(
    String localId,
    ExerciseDto exercise,
    String notes,
    List<TrainingSetDto> sets
) {
}
