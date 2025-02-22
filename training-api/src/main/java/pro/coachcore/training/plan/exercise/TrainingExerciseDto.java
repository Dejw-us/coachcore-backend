package pro.coachcore.training.plan.exercise;


import java.util.List;

import pro.coachcore.training.catalog.exercise.CatalogExerciseDto;
import pro.coachcore.training.plan.set.TrainingSetDto;

public record TrainingExerciseDto(
    String id,
    CatalogExerciseDto catalogExercise,
    String notes,
    List<TrainingSetDto> sets
) {
}
