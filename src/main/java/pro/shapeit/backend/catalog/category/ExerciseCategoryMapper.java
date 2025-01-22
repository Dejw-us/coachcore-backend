package pro.shapeit.backend.catalog.category;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseCategoryMapper {
  ExerciseCategoryDto map(ExerciseCategory exerciseCategory);
}
