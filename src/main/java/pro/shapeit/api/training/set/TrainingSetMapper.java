package pro.shapeit.api.training.set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingSetMapper {
  TrainingSetDto map(TrainingSet model);

  TrainingSet map(TrainingSetDto dto);
}

