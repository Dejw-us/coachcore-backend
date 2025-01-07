package pro.shapeit.api.training.plan;

import pro.shapeit.api.training.goal.TrainingGoalDto;
import pro.shapeit.api.training.unit.TrainingUnitDto;

import java.util.List;


public record TrainingPlanDto(
    String localId,
    String name,
    String description,
    List<TrainingUnitDto> units,
    List<TrainingGoalDto> goals
) {
}
