package pro.coachcore.training.catalog.exercise;


import io.swagger.v3.oas.annotations.media.Schema;
import pro.coachcore.training.catalog.category.ExerciseCategoryDto;

@Schema
public record CatalogExerciseDto(
    String id,
    String name,
    ExerciseCategoryDto category
) {
}
