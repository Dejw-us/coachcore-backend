package pro.shapeit.api.training.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.common.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
class TrainingPlanService {
  private final TrainingPlanRepository trainingPlanRepository;

  List<TrainingPlan> findAll() {
    return trainingPlanRepository.findAll();
  }

  TrainingPlan findByLocalId(String localId) throws ResourceNotFoundException {
    return trainingPlanRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Training plan does not exist"));
  }
}
