package pro.coachcore.training.catalog.exercise;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pro.coachcore.training.catalog.category.ExerciseCategoryMapper;

@Mapper(uses = ExerciseCategoryMapper.class, componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CatalogExerciseMapper {
  @Mapping(source = "localId", target = "id")
  CatalogExerciseDto map(CatalogExercise model);
}
