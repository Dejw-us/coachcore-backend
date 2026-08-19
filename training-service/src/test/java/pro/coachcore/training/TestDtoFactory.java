package pro.coachcore.training;

import java.util.List;

import lombok.experimental.UtilityClass;
import pro.coachcore.training.catalog.exercise.CreateCatalogExerciseDto;
import pro.coachcore.training.plan.CreateTrainingPlanDto;
import pro.coachcore.training.plan.UpdateTrainingPlanDto;
import pro.coachcore.training.plan.exercise.TrainingExercise.IntensityType;
import pro.coachcore.training.plan.exercise.TrainingExercise.WeightType;
import pro.coachcore.training.plan.exercise.UpdateTrainingExerciseDto;
import pro.coachcore.training.plan.parameter.ParameterDisplay.DisplayUpdater;
import pro.coachcore.training.plan.parameter.UpdateParameterDisplayDto;
import pro.coachcore.training.plan.set.UpdateTrainingSetDto;
import pro.coachcore.training.plan.unit.CreateTrainingUnitDto;
import pro.coachcore.training.plan.unit.UpdateTrainingUnitDto;

@UtilityClass
public class TestDtoFactory {
  public static CreateTrainingPlanDto createPlanDto() {
    return new CreateTrainingPlanDto("Plan A", "Description A", List.of("Goal1", "Goal2"), 1, true);
  }

  public static CreateTrainingUnitDto createUnitDto() {
    return new CreateTrainingUnitDto("MONDAY", "some notes", "name");
  }

  public static UpdateTrainingUnitDto updateUnitDto() {
    return new UpdateTrainingUnitDto("FRIDAY", "super crazy notes", "new name");
  }

  public static UpdateTrainingPlanDto updatePlanDto() {
    return new UpdateTrainingPlanDto("Plan B", "Description B");
  }

  public static UpdateTrainingExerciseDto updateExerciseDto() {
    return new UpdateTrainingExerciseDto("new notes babe", WeightType.KG, IntensityType.RPE);
  }

  public static UpdateTrainingSetDto updateSetDto() {
    return new UpdateTrainingSetDto(5, 5D, "2-3-2-x", 60D, 4D);
  }

  public static CreateCatalogExerciseDto createCatalogExerciseDto() {
    return new CreateCatalogExerciseDto("running");
  }

  public static UpdateParameterDisplayDto updateDisplayDto() {
    return new UpdateParameterDisplayDto(DisplayUpdater.INTENSITY, true);
  }
}
