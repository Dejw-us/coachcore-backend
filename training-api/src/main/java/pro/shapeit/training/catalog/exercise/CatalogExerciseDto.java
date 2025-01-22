package pro.shapeit.training.catalog.exercise;


import pro.shapeit.training.catalog.category.ExerciseCategoryDto;

public record CatalogExerciseDto(
    String id,
    String name,
    ExerciseCategoryDto category
) {
}
