package pro.coachcore.training.plan.save;

import java.util.List;

import org.mapstruct.Mapper;

import pro.coachcore.training.plan.TrainingPlanMapper;

@Mapper(componentModel = "spring", uses = TrainingPlanMapper.class)
public interface SavedPlanMapper {
  SavedPlanDto map(SavedPlan plan);

  List<SavedPlanDto> map(List<SavedPlan> plans);
}
