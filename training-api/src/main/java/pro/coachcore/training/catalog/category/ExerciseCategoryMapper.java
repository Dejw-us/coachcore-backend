package pro.coachcore.training.catalog.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExerciseCategoryMapper {
  @Mapping(source = "localId", target = "id")
  ExerciseCategoryDto map(ExerciseCategory exerciseCategory);
}
