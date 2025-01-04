package pro.shapeit.api.training.plan;

import org.springframework.stereotype.Service;
import pro.shapeit.api.training.exercise.ExerciseCategory;
import pro.shapeit.api.training.exercise.ExerciseDto;
import pro.shapeit.api.training.exercise.TrainingExerciseDto;
import pro.shapeit.api.training.set.TrainingSet;
import pro.shapeit.api.training.set.TrainingSetDto;
import pro.shapeit.api.training.unit.TrainingUnitDto;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@Service
public class TrainingPlanService {


  public List<TrainingPlanDto> getDemoTrainingPlans() {
    var pullUp = new ExerciseDto(uuid(), "pull-up", new ExerciseCategory());
    var squat = new ExerciseDto(uuid(), "squat", new ExerciseCategory());
    var deadLift = new ExerciseDto(uuid(), "dead lift", new ExerciseCategory());
    var benchPress = new ExerciseDto(uuid(), "bench press", new ExerciseCategory());
    var lunges = new ExerciseDto(uuid(), "lunges", new ExerciseCategory());
    var bicepCurl = new ExerciseDto(uuid(), "bicep curl", new ExerciseCategory());

    var unit1 = new TrainingUnitDto(uuid(), "No excuses", DayOfWeek.MONDAY, List.of(
        new TrainingExerciseDto(uuid(), pullUp, null, List.of(
            new TrainingSetDto(uuid(), 10, 10D, TrainingSet.IntensityType.RPE, null, 1.5D),
            new TrainingSetDto(uuid(), 8, 8.5D, TrainingSet.IntensityType.RPE, null, 1.5D),
            new TrainingSetDto(uuid(), 6, 8D, TrainingSet.IntensityType.RPE, null, 1.5D)
        )),
        new TrainingExerciseDto(uuid(), squat, "Thick asss...", List.of(
            new TrainingSetDto(uuid(), 6, 9.5D, TrainingSet.IntensityType.RPE, "2-4-3-2", 3D),
            new TrainingSetDto(uuid(), 6, 8.5D, TrainingSet.IntensityType.RPE, "2-4-3-2", 3D),
            new TrainingSetDto(uuid(), 4, 8.5D, TrainingSet.IntensityType.RPE, "x-x-x-x", 3D)
        ))
    ));

    var unit2 = new TrainingUnitDto(uuid(), "Skinny bitch!", DayOfWeek.FRIDAY, List.of(
        new TrainingExerciseDto(uuid(), benchPress, "I'm a MAN!", List.of(
            new TrainingSetDto(uuid(), 12, 7D, TrainingSet.IntensityType.RPE, "1-2-2-x", 2D),
            new TrainingSetDto(uuid(), 10, 8D, TrainingSet.IntensityType.RPE, "1-2-2-x", 3D),
            new TrainingSetDto(uuid(), 8, 9D, TrainingSet.IntensityType.RPE, "1-2-2-x", 1.5D)
        )),
        new TrainingExerciseDto(uuid(), lunges, "Stable legs bro", List.of(
            new TrainingSetDto(uuid(), 16, 9D, TrainingSet.IntensityType.RPE, "1-4-2-2", 3D),
            new TrainingSetDto(uuid(), 14, 10D, TrainingSet.IntensityType.RPE, "1-4-2-2", 3D),
            new TrainingSetDto(uuid(), 12, 9.5D, TrainingSet.IntensityType.RPE, "1-4-2-2", 3D)
        ))
    ));
    return List.of(new TrainingPlanDto(
        UUID.randomUUID().toString(),
        "Plan na masę",
        List.of(unit1, unit2),
        List.of("Trenujemy psychę", "Łapa ma być duża")
    ));
  }

  private static String uuid() {
    return UUID.randomUUID().toString();
  }
}
