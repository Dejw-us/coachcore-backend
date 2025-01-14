package pro.shapeit.api.training.plan.unit;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pro.shapeit.api.training.plan.unit.exercise.TrainingExerciseMapper;
import pro.shapeit.api.training.plan.unit.exercise.set.TrainingSetMapper;

@Mapper(uses = {TrainingSetMapper.class, TrainingExerciseMapper.class}, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingUnitMapper {
  TrainingUnitDto map(TrainingUnit model);
}
