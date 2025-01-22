package pro.shapeit.training.plan;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.shapeit.training.plan.goal.TrainingGoalMapper;
import pro.shapeit.training.plan.unit.exercise.set.TrainingSetMapper;

import java.util.List;

@Mapper(uses = {TrainingSetMapper.class, TrainingGoalMapper.class}, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingPlanMapper {
  @Mapping(target = "id", source = "localId")
  TrainingPlanDto map(TrainingPlan model);

  List<TrainingPlanDto> map(List<TrainingPlan> models);
}
