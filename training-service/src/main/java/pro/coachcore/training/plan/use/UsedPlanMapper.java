package pro.coachcore.training.plan.use;

import java.util.List;

import org.mapstruct.Mapper;

import pro.coachcore.training.plan.TrainingPlanMapper;

@Mapper(componentModel = "spring", uses = TrainingPlanMapper.class)
public interface UsedPlanMapper {
  UsedPlanDto map(UsedPlan usedPlan);

  List<UsedPlanDto> map(List<UsedPlan> usedPlans);
}
