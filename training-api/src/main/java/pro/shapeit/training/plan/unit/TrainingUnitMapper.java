package pro.shapeit.training.plan.unit;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.shapeit.training.plan.unit.exercise.TrainingExerciseMapper;
import pro.shapeit.training.plan.unit.exercise.set.TrainingSetMapper;

import java.util.List;

@Mapper(uses = {TrainingSetMapper.class, TrainingExerciseMapper.class}, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingUnitMapper {
  @Mapping(source = "localId", target = "id")
  TrainingUnitDto map(TrainingUnit model);

  List<TrainingUnitDto> map(List<TrainingUnit> models);
}
