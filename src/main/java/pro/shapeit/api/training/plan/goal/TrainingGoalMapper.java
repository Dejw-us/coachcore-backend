package pro.shapeit.api.training.plan.goal;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainingGoalMapper {
  TrainingGoalDto map(TrainingGoal model);
}
