package pro.coachcore.training.plan;

import java.util.List;

import pro.coachcore.training.plan.goal.TrainingGoalDto;

public record TrainingPlanDto(String id, String createdBy, String name, String description,
    Integer weeks, List<TrainingGoalDto> goals, List<String> tags) {
}
