package pro.shapeit.api.training.unit;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pro.shapeit.api.training.exercise.TrainingExerciseMapper;
import pro.shapeit.api.training.set.TrainingSetMapper;

@Mapper(uses = {TrainingSetMapper.class, TrainingExerciseMapper.class}, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingUnitMapper {
  TrainingUnitDto map(TrainingUnit model);
}
