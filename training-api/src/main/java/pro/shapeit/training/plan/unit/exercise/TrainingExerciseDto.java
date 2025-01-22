package pro.shapeit.training.plan.unit.exercise;


import pro.shapeit.training.catalog.exercise.CatalogExerciseDto;
import pro.shapeit.training.plan.unit.exercise.set.TrainingSetDto;

import java.util.List;

public record TrainingExerciseDto(
    String id,
    CatalogExerciseDto catalogExercise,
    String notes,
    List<TrainingSetDto> sets
) {
}
