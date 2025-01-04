package pro.shapeit.api.training.plan;

import pro.shapeit.api.training.unit.TrainingUnitDto;

import java.util.List;


public record TrainingPlanDto(
    String localId,
    String description,
    List<TrainingUnitDto> units,
    List<String> goals
) {
}
