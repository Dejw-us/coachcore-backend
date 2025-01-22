package pro.shapeit.training.plan.unit.exercise;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pro.shapeit.backend.training.plan.unit.exercise.set.TrainingSetMapper;

@Mapper(uses = TrainingSetMapper.class, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingExerciseMapper {
  TrainingExerciseDto map(TrainingExercise exercise);
}
