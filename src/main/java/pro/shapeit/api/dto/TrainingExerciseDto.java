package pro.shapeit.api.dto;

import pro.shapeit.api.model.TrainingExercise;

import java.util.List;

public record TrainingExerciseDto(
    String localId,
    ExerciseDto exercise,
    String notes,
    List<TrainingSetDto> sets
) {
  public static TrainingExerciseDto from(TrainingExercise exercise) {
    return new TrainingExerciseDto(
        exercise.getLocalId(),
        ExerciseDto.from(exercise.getExercise()),
        exercise.getNotes(),
        exercise.getSets().stream().map(TrainingSetDto::from).toList()
    );
  }
}
