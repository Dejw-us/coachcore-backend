package pro.shapeit.api.training.plan;

import java.util.List;

public record CreateTrainingPlanDto(String name, String description, List<String> goals) {
}
