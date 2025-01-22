package pro.shapeit.training.plan;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pro.shapeit.training.plan.goal.TrainingGoalMapper;
import pro.shapeit.training.plan.unit.exercise.set.TrainingSetMapper;

import java.util.List;

@Mapper(uses = {TrainingSetMapper.class, TrainingGoalMapper.class}, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingPlanMapper {
  TrainingPlanDto map(TrainingPlan model);

  List<TrainingPlanDto> map(List<TrainingPlan> models);
}
