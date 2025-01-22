package pro.shapeit.training.catalog.category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record ExerciseCategoryDto(
    String id,
    String name,
    String description
) {
}
