package pro.shapeit.api.training.plan.unit.exercise.set;


public record TrainingSetDto(
    String localId,
    Integer reps,
    Double intensity,
    TrainingSet.IntensityType intensityType,
    String rate,
    Double restSeconds
) {
}
