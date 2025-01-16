package pro.shapeit.api.training.plan;

import pro.shapeit.api.training.plan.goal.TrainingGoalDto;
import pro.shapeit.api.training.plan.unit.TrainingUnitDto;

import java.util.List;


public record TrainingPlanDto(
    String localId,
    String name,
    String description,
    List<TrainingGoalDto> goals
) {
}
