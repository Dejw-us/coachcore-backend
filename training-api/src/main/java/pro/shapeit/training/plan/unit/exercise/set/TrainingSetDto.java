package pro.shapeit.training.plan.unit.exercise.set;


public record TrainingSetDto(
    String id,
    Integer reps,
    Double intensity,
    TrainingSet.IntensityType intensityType,
    String rate,
    Double restSeconds,
    Double weight,
    TrainingSet.WeightType weightType
) {
}
