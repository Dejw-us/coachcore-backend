package pro.shapeit.api.training.goal;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainingGoalMapper {
  TrainingGoalDto map(TrainingGoal model);
}
