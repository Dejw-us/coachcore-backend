package pro.shapeit.api.training.unit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.common.id.LocalId;
import pro.shapeit.api.training.plan.TrainingPlan;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingUnitService {
  private final TrainingUnitRepository trainingUnitRepository;

  public TrainingUnit saveTrainingUnit(CreateTrainingUnitDto dto, TrainingPlan parent) {
    var unit = new TrainingUnit();

    unit.setLocalId(LocalId.random());
    unit.setDayOfWeek(dto.dayOfWeek());
    unit.setTrainingPlan(parent);

    return trainingUnitRepository.save(unit);
  }

  public List<TrainingUnit> findTrainingUnits(String localTrainingPlanId) {
    return trainingUnitRepository.findByTrainingPlan_LocalId(localTrainingPlanId);
  }

  public TrainingUnit updateTrainingUnit(String localId, UpdateTrainingUnitDto dto) {
    var unit = findTrainingUnit(localId);

    if (dto.notes() != null) {
      unit.setNotes(dto.notes());
    }
    if (dto.dayOfWeek() != null) {
      unit.setDayOfWeek(dto.dayOfWeek());
    }

    return trainingUnitRepository.save(unit);
  }

  public TrainingUnit findTrainingUnit(String localId) throws ResourceNotFoundException {
    return trainingUnitRepository.findByLocalId(localId)
        .orElseThrow(() -> new ResourceNotFoundException("Training unit does not exist"));
  }
}
