package pro.shapeit.api.training.plan;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pro.shapeit.api.training.plan.goal.TrainingGoal;
import pro.shapeit.api.training.plan.goal.TrainingGoalMapper;
import pro.shapeit.api.training.plan.unit.exercise.set.TrainingSetMapper;

import java.util.List;

@Mapper(uses = {TrainingSetMapper.class, TrainingGoalMapper.class}, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingPlanMapper {
  TrainingPlanDto map(TrainingPlan model);

  default List<String> map(List<TrainingGoal> goals) {
    return goals.stream()
        .map(TrainingGoal::getDescription)
        .toList();
  }
}
