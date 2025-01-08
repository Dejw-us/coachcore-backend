package pro.shapeit.api.training.set;

public record UpdateTrainingSetDto(
    Integer reps,
    Double intensity,
    TrainingSet.IntensityType intensityType,
    String rate,
    Double rest
) {
}
