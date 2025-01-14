package pro.shapeit.api.training.plan.unit.exercise;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pro.shapeit.api.training.plan.unit.exercise.set.TrainingSetMapper;

@Mapper(uses = TrainingSetMapper.class, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingExerciseMapper {
  TrainingExerciseDto map(TrainingExercise exercise);

  TrainingExercise map(TrainingExerciseDto dto);
}
