package pro.shapeit.api.dto;

import pro.shapeit.api.constant.IntensityType;
import pro.shapeit.api.model.TrainingSet;

public record TrainingSetDto(
    String localId,
    Integer reps,
    Double intensity,
    IntensityType intensityType,
    String rate,
    Double rest
) {
  public static TrainingSetDto from(TrainingSet set) {
    return new TrainingSetDto(
        set.getLocalId(),
        set.getReps(),
        set.getIntensity(),
        set.getIntensityType(),
        set.getRate(),
        set.getRest()
    );
  }
}
