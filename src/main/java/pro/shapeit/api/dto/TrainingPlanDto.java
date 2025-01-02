package pro.shapeit.api.dto;

import pro.shapeit.api.model.TrainingGoal;
import pro.shapeit.api.model.TrainingPlan;

import java.util.List;


public record TrainingPlanDto(
    String localId,
    String description,
    List<TrainingUnitDto> units,
    List<String> goals
) {
  public static TrainingPlanDto from(TrainingPlan plan) {
    return new TrainingPlanDto(
        plan.getLocalId(),
        plan.getDescription(),
        plan.getUnits().stream().map(TrainingUnitDto::from).toList(),
        plan.getGoals().stream().map(TrainingGoal::getDescription).toList()
    );
  }
}
