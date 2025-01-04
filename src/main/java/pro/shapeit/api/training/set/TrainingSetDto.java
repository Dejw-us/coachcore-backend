package pro.shapeit.api.training.set;


public record TrainingSetDto(
    String localId,
    Integer reps,
    Double intensity,
    TrainingSet.IntensityType intensityType,
    String rate,
    Double rest
) {
}
