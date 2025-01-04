package pro.shapeit.api.training.plan;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pro.shapeit.api.training.set.TrainingSetMapper;

import java.util.List;

@Mapper(uses = TrainingSetMapper.class, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingPlanMapper {
  TrainingPlanDto map(TrainingPlan model);

  default List<String> map(List<TrainingGoal> goals) {
    return goals.stream()
        .map(TrainingGoal::getDescription)
        .toList();
  }
}
