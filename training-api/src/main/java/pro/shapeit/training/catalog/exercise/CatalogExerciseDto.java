package pro.shapeit.training.catalog.exercise;


import io.swagger.v3.oas.annotations.media.Schema;
import pro.shapeit.training.catalog.category.ExerciseCategoryDto;

@Schema
public record CatalogExerciseDto(
    String id,
    String name,
    ExerciseCategoryDto category
) {
}
