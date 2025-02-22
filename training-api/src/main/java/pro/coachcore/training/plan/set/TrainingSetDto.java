package pro.coachcore.training.plan.set;


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
