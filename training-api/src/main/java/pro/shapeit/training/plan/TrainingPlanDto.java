package pro.shapeit.training.plan;

import pro.shapeit.training.plan.goal.TrainingGoalDto;

import java.util.List;

public record TrainingPlanDto(
    String localId,
    String name,
    String description,
    List<TrainingGoalDto> goals
) {
}
