package pro.shapeit.training.catalog.category;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseCategoryMapper {
  ExerciseCategoryDto map(ExerciseCategory exerciseCategory);
}
