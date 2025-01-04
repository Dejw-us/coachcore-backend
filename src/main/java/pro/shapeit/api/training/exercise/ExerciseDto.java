package pro.shapeit.api.training.exercise;

public record ExerciseDto(
    String localId,
    String name,
    ExerciseCategory category
) {
}
