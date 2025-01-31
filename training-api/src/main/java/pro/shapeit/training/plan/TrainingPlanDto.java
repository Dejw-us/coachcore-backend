package pro.shapeit.training.plan;

import pro.shapeit.training.plan.goal.TrainingGoalDto;

import java.util.List;

public record TrainingPlanDto(
    String id,
    String createdBy,
    String name,
    String description,
    List<TrainingGoalDto> goals
) {
}
