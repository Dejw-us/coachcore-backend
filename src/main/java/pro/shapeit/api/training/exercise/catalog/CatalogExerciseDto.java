package pro.shapeit.api.training.exercise.catalog;

import pro.shapeit.api.training.exercise.category.ExerciseCategory;
import pro.shapeit.api.training.exercise.category.ExerciseCategoryDto;

public record CatalogExerciseDto(
    String localId,
    String name,
    ExerciseCategoryDto category
) {
}
