package pro.shapeit.training.plan.goal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingGoalMapper {
  @Mapping(target = "id", source = "localId")
  TrainingGoalDto map(TrainingGoal model);

  List<TrainingGoalDto> map(List<TrainingGoal> models);
}
