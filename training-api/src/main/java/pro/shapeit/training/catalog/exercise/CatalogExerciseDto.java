package pro.shapeit.training.catalog.exercise;


import pro.shapeit.training.catalog.category.ExerciseCategoryDto;

public record CatalogExerciseDto(
    String localId,
    String name,
    ExerciseCategoryDto category
) {
}
