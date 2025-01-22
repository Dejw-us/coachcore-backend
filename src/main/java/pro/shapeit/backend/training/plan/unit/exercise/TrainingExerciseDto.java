package pro.shapeit.backend.training.plan.unit.exercise;

import pro.shapeit.backend.catalog.exercise.CatalogExerciseDto;
import pro.shapeit.backend.training.plan.unit.exercise.set.TrainingSetDto;

import java.util.List;

public record TrainingExerciseDto(
    String localId,
    CatalogExerciseDto catalogExercise,
    String notes,
    List<TrainingSetDto> sets
) {
}
