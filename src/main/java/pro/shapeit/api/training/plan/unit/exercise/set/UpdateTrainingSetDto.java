package pro.shapeit.api.training.plan.unit.exercise.set;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateTrainingSetDto(
    @Min(1)
    Integer reps,
    @Min(1L)
    @Max(10L)
    Double intensity,
    TrainingSet.IntensityType intensityType,
    String rate,
    Double rest,
    Double weight,
    TrainingSet.WeightType weightType
) {
}
