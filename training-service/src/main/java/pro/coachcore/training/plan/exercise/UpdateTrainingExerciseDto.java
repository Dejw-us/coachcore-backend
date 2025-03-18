package pro.coachcore.training.plan.exercise;

import jakarta.validation.constraints.Size;
import pro.coachcore.training.plan.exercise.TrainingExercise.IntensityType;
import pro.coachcore.training.plan.exercise.TrainingExercise.WeightType;

public record UpdateTrainingExerciseDto(
    @Size(max = 200) String notes,
    WeightType weightType,
    IntensityType intensityType) {
}
