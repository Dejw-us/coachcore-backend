package pro.shapeit.api.dto;

import pro.shapeit.api.model.TrainingUnit;

import java.time.DayOfWeek;
import java.util.List;

public record TrainingUnitDto(
    String localId,
    String notes,
    DayOfWeek dayOfWeek,
    List<TrainingExerciseDto> exercises
) {
  public static TrainingUnitDto from(TrainingUnit unit) {
    return new TrainingUnitDto(
        unit.getLocalId(),
        unit.getNotes(),
        unit.getDayOfWeek(),
        unit.getExercises().stream().map(TrainingExerciseDto::from).toList()
    );
  }
}
