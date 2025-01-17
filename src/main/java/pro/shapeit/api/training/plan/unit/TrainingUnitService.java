package pro.shapeit.api.training.plan.unit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.plan.TrainingPlan;
import pro.shapeit.api.training.plan.TrainingPlanRepository;

import java.util.List;

import static pro.shapeit.api.common.util.ServiceUtils.updateIfNotNull;

@Service
@RequiredArgsConstructor
public class TrainingUnitService {
  private final TrainingUnitRepository trainingUnitRepository;
  private final TrainingPlanRepository trainingPlanRepository;

  // --- Find methods ---

  public List<TrainingUnit> findAllTrainingUnitsByTrainingPlanLocalId(
      String planLocalId
  ) throws ResourceNotFoundException {
    if (!trainingPlanRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException("Training plan does not exist");
    }
    return trainingUnitRepository.findAllByTrainingPlan_LocalId(planLocalId);
  }

  public TrainingUnit findTrainingUnitByTrainingPlanLocalIdAndLocalId(
      String planLocalId,
      String unitLocalId
  ) throws ResourceNotFoundException {
    return trainingUnitRepository.findByTrainingPlan_LocalIdAndLocalId(planLocalId, unitLocalId)
        .orElseThrow(ResourceNotFoundException.supplier("Training unit does not exist"));
  }

  // --- Save methods ---

  public TrainingUnit saveTrainingUnit(TrainingPlan plan, CreateTrainingUnitDto dto) {
    var unit = new TrainingUnit();
    unit.setDayOfWeek(dto.dayOfWeek());
    unit.setTrainingPlan(plan);

    return trainingUnitRepository.save(unit);
  }

  // --- Update methods ---

  public TrainingUnit updateTrainingUnit(TrainingUnit unit, UpdateTrainingUnitDto dto) {
    updateIfNotNull(dto.dayOfWeek(), unit::setDayOfWeek);
    updateIfNotNull(dto.notes(), unit::setNotes);
    updateIfNotNull(dto.name(), unit::setName);

    return trainingUnitRepository.save(unit);
  }

  // --- Delete methods ---

  public boolean deleteTrainingUnitByTrainingPlanLocalIdAndLocalId(String planLocalId, String unitLocalId) {
    return trainingUnitRepository.deleteByTrainingPlan_LocalIdAndLocalIdWithCount(planLocalId, unitLocalId) > 0;
  }
}
