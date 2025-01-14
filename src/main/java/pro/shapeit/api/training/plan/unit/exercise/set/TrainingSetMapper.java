package pro.shapeit.api.training.plan.unit.exercise.set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingSetMapper {
  TrainingSetDto map(TrainingSet model);

  TrainingSet map(TrainingSetDto dto);
}

