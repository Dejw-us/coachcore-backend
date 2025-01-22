package pro.shapeit.training.plan.unit.exercise;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.shapeit.training.plan.unit.exercise.set.TrainingSetMapper;

@Mapper(uses = TrainingSetMapper.class, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingExerciseMapper {
  @Mapping(target = "id", source = "localId")
  TrainingExerciseDto map(TrainingExercise exercise);
}
