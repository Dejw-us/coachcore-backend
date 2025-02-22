package pro.coachcore.training;

import java.util.List;

import pro.coachcore.training.catalog.exercise.CreateCatalogExerciseDto;
import pro.coachcore.training.plan.CreateTrainingPlanDto;
import pro.coachcore.training.plan.exercise.UpdateTrainingExerciseDto;
import pro.coachcore.training.plan.set.UpdateTrainingSetDto;
import pro.coachcore.training.plan.unit.CreateTrainingUnitDto;
import pro.coachcore.training.plan.unit.UpdateTrainingUnitDto;

public class TestDtos {
  public static final CreateTrainingPlanDto CREATE_PLAN_DTO = new CreateTrainingPlanDto(
      "Plan A",
      "Description A",
      List.of("Goal1", "Goal2")
  );

  public static final CreateTrainingUnitDto CREATE_UNIT_DTO = new CreateTrainingUnitDto("MONDAY", "some notes", "name");

  public static final UpdateTrainingUnitDto UPDATE_UNIT_DTO = new UpdateTrainingUnitDto(
      "FRIDAY",
      "super crazy notes",
      "new name"
  );

  public static final UpdateTrainingExerciseDto UPDATE_EXERCISE_DTO = new UpdateTrainingExerciseDto("new notes babe");

  public static final UpdateTrainingSetDto UPDATE_SET_DTO = new UpdateTrainingSetDto(
      5, 5D, "RIR", "2-3-2-x", 60D, 4D, "KG"
  );

  public static final CreateCatalogExerciseDto CREATE_CATALOG_EXERCISE_DTO = new CreateCatalogExerciseDto("running");
}
