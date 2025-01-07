package pro.shapeit.api.training.exercise.category;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseCategoryMapper {
  ExerciseCategoryDto map(ExerciseCategory exerciseCategory);
}
