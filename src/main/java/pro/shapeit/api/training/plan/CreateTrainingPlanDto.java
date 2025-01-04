package pro.shapeit.api.training.plan;

import java.util.List;

public record CreateTrainingPlanDto(String description, List<String> goals) {
}
