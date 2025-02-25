package pro.coachcore.training.plan;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pro.coachcore.training.plan.goal.TrainingGoalMapper;
import java.util.List;

@Mapper(uses = {TrainingGoalMapper.class}, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingPlanMapper {
  @Mapping(target = "id", source = "localId")
  TrainingPlanDto map(TrainingPlan model);

  List<TrainingPlanDto> map(List<TrainingPlan> models);
}
