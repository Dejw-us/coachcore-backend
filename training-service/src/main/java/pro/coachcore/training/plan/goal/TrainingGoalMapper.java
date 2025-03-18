package pro.coachcore.training.plan.goal;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainingGoalMapper {
  @Mapping(target = "id", source = "localId")
  TrainingGoalDto map(TrainingGoal model);

  List<TrainingGoalDto> map(List<TrainingGoal> models);
}
