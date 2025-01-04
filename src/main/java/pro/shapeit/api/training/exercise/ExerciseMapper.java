package pro.shapeit.api.training.exercise;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface  ExerciseMapper {
  ExerciseDto map(Exercise model);

  Exercise map(ExerciseDto dto);
}
