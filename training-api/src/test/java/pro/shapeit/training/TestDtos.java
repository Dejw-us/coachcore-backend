package pro.shapeit.training;

import pro.shapeit.training.plan.CreateTrainingPlanDto;
import pro.shapeit.training.plan.unit.CreateTrainingUnitDto;
import pro.shapeit.training.plan.unit.UpdateTrainingUnitDto;
import pro.shapeit.training.plan.unit.exercise.UpdateTrainingExerciseDto;
import pro.shapeit.training.plan.unit.exercise.set.UpdateTrainingSetDto;

import java.util.List;

public class TestDtos {
  public static final CreateTrainingPlanDto CREATE_PLAN_DTO = new CreateTrainingPlanDto(
      "Plan A",
      "Description A",
      List.of("Goal1", "Goal2")
  );

  public static final CreateTrainingUnitDto CREATE_UNIT_DTO = new CreateTrainingUnitDto("MONDAY");

  public static final UpdateTrainingUnitDto UPDATE_UNIT_DTO = new UpdateTrainingUnitDto(
      "FRIDAY",
      "super crazy notes",
      "new name"
  );

  public static final UpdateTrainingExerciseDto UPDATE_EXERCISE_DTO = new UpdateTrainingExerciseDto("new notes babe");

  public static final UpdateTrainingSetDto UPDATE_SET_DTO = new UpdateTrainingSetDto(
      5, 5D, "RIR", "2-3-2-x", 60D, 4D, "KG"
  );
}
