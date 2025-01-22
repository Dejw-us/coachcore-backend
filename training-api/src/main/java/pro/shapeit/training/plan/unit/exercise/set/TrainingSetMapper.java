package pro.shapeit.training.plan.unit.exercise.set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingSetMapper {
  @Mapping(source = "localId", target = "id")
  TrainingSetDto map(TrainingSet model);
}

