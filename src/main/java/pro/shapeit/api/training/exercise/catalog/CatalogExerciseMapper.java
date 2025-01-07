package pro.shapeit.api.training.exercise.catalog;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pro.shapeit.api.training.exercise.category.ExerciseCategoryMapper;

@Mapper(uses = ExerciseCategoryMapper.class, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CatalogExerciseMapper {
  CatalogExerciseDto map(CatalogExercise model);

  CatalogExercise map(CatalogExerciseDto dto);
}
