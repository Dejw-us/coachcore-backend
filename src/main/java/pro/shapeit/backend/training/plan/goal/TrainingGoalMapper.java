package pro.shapeit.backend.training.plan.goal;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingGoalMapper {
  TrainingGoalDto map(TrainingGoal model);
  List<TrainingGoalDto> map(List<TrainingGoal> models);
}
