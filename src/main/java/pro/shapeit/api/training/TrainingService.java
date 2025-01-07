package pro.shapeit.api.training;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.training.plan.TrainingPlanService;
import pro.shapeit.api.training.unit.TrainingUnitService;

@Service
@RequiredArgsConstructor
public class TrainingService {
  private final TrainingPlanService trainingPlanService;
  private final TrainingUnitService trainingUnitService;


}
