package pro.coachcore.training.catalog.exercise;

import pro.coachcore.training.catalog.category.ExerciseCategoryDto;

public record CatalogExerciseDto(String id, String name, ExerciseCategoryDto category) {
}
