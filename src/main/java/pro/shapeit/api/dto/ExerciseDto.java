package pro.shapeit.api.dto;

import pro.shapeit.api.model.Exercise;
import pro.shapeit.api.model.ExerciseCategory;

public record ExerciseDto(
    String localId,
    String name,
    String category
) {
  public static ExerciseDto from(Exercise exercise) {
    return new ExerciseDto(
        exercise.getLocalId(),
        exercise.getName(),
        exercise.getCategory().getName()
    );
  }
}
