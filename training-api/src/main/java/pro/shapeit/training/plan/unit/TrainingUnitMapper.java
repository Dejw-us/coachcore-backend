package pro.shapeit.training.plan.unit;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pro.shapeit.backend.training.plan.unit.exercise.TrainingExerciseMapper;
import pro.shapeit.backend.training.plan.unit.exercise.set.TrainingSetMapper;

import java.util.List;

@Mapper(uses = {TrainingSetMapper.class, TrainingExerciseMapper.class}, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingUnitMapper {
  TrainingUnitDto map(TrainingUnit model);

  List<TrainingUnitDto> map(List<TrainingUnit> models);
}
