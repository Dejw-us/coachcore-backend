package pro.coachcore.training.plan.exercise;

import pro.coachcore.training.catalog.exercise.CatalogExerciseDto;
import pro.coachcore.training.plan.exercise.TrainingExercise.IntensityType;
import pro.coachcore.training.plan.exercise.TrainingExercise.WeightType;

public record TrainingExerciseDto(
    String id,
    CatalogExerciseDto catalogExercise,
    String notes,
    IntensityType intensityType,
    WeightType weightType,
    Long index) {
}
