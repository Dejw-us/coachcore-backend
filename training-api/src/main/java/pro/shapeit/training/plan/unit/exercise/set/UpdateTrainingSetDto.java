package pro.shapeit.training.plan.unit.exercise.set;

import jakarta.validation.constraints.*;
import pro.coachcore.validation.annotation.ValidEnum;

public record UpdateTrainingSetDto(
    @Min(1L)
    @Max(10000L)
    Integer reps,

    @DecimalMin("1.0")
    @DecimalMax("10.0")
    Double intensity,

    @ValidEnum(value = TrainingSet.IntensityType.class, acceptNull = true)
    String intensityType,

    @Pattern(
        regexp = "^([0-9x]-){3}[0-9x]$",
        message = "Rate must match the pattern '1-1-1-1' where each part is a number or 'x'"
    )
    String rate,

    @DecimalMin("0.0")
    @DecimalMax("3600.0")
    Double restSeconds,

    @DecimalMin("0.0")
    @DecimalMax("10000.0")
    Double weight,

    @ValidEnum(value = TrainingSet.WeightType.class, acceptNull = true)
    String weightType
) {
}
