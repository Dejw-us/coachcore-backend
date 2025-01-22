package pro.shapeit.backend.catalog.exercise;

import pro.shapeit.backend.catalog.category.ExerciseCategoryDto;

public record CatalogExerciseDto(
    String localId,
    String name,
    ExerciseCategoryDto category
) {
}
