package pro.shapeit.api.catalog.exercise;

import pro.shapeit.api.catalog.category.ExerciseCategoryDto;

public record CatalogExerciseDto(
    String localId,
    String name,
    ExerciseCategoryDto category
) {
}
