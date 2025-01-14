package pro.shapeit.api.training.plan.unit.exercise.set;

public record UpdateTrainingSetDto(
    Integer reps,
    Double intensity,
    TrainingSet.IntensityType intensityType,
    String rate,
    Double rest
) {
}
