package pro.coachcore.training.plan;

import java.util.Collection;
import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import pro.coachcore.training.plan.goal.TrainingGoalMapper;
import pro.coachcore.training.plan.save.SavedPlanService;

@Mapper(uses = {
    TrainingGoalMapper.class,
    SavedPlanService.class }, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class TrainingPlanMapper {
  @Autowired
  protected SavedPlanService savedPlanService;

  @Mapping(target = "id", source = "localId")
  @Mapping(target = "users", expression = "java(savedPlanService.getUsersAmount(plan.getLocalId()))")
  public abstract TrainingPlanDto map(
      TrainingPlan plan);

  public abstract List<TrainingPlanDto> map(Collection<TrainingPlan> plans);
}
