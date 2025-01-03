package pro.shapeit.api.service;

import org.springframework.stereotype.Service;
import pro.shapeit.api.constant.IntensityType;
import pro.shapeit.api.dto.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@Service
public class TrainingPlanService {
  public List<TrainingPlanDto> getDemoTrainingPlans() {
    var pullUp = new ExerciseDto(uuid(), "pull-up", "Calisthenics");
    var squat = new ExerciseDto(uuid(), "squat", "Legs");
    var deadLift = new ExerciseDto(uuid(), "dead lift", "Legs/Back");
    var benchPress = new ExerciseDto(uuid(), "bench press", "Upper body");
    var lunges = new ExerciseDto(uuid(), "lunges", "Legs");
    var bicepCurl = new ExerciseDto(uuid(), "bicep curl", "Isolation");

    var unit1 = new TrainingUnitDto(uuid(), "No excuses", DayOfWeek.MONDAY, List.of(
        new TrainingExerciseDto(uuid(), pullUp, null, List.of(
            new TrainingSetDto(uuid(), 10, 10D, IntensityType.RPE, null, 1.5D),
            new TrainingSetDto(uuid(), 8, 8.5D, IntensityType.RPE, null, 1.5D),
            new TrainingSetDto(uuid(), 6, 8D, IntensityType.RPE, null, 1.5D)
        )),
        new TrainingExerciseDto(uuid(), squat, "Thick asss...", List.of(
            new TrainingSetDto(uuid(), 6, 9.5D, IntensityType.RPE, "2-4-3-2", 3D),
            new TrainingSetDto(uuid(), 6, 8.5D, IntensityType.RPE, "2-4-3-2", 3D),
            new TrainingSetDto(uuid(), 4, 8.5D, IntensityType.RPE, "x-x-x-x", 3D)
        ))
    ));

    var unit2 = new TrainingUnitDto(uuid(), "Skinny bitch!", DayOfWeek.FRIDAY, List.of(
        new TrainingExerciseDto(uuid(), benchPress, "I'm a MAN!", List.of(
            new TrainingSetDto(uuid(), 12, 7D, IntensityType.RPE, "1-2-2-x", 2D),
            new TrainingSetDto(uuid(), 10, 8D, IntensityType.RPE, "1-2-2-x", 3D),
            new TrainingSetDto(uuid(), 8, 9D, IntensityType.RPE, "1-2-2-x", 1.5D)
        )),
        new TrainingExerciseDto(uuid(), lunges, "Stable legs bro", List.of(
            new TrainingSetDto(uuid(), 16, 9D, IntensityType.RPE, "1-4-2-2", 3D),
            new TrainingSetDto(uuid(), 14, 10D, IntensityType.RPE, "1-4-2-2", 3D),
            new TrainingSetDto(uuid(), 12, 9.5D, IntensityType.RPE, "1-4-2-2", 3D)
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
