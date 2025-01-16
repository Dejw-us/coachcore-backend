package pro.shapeit.api.training.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.catalog.exercise.CatalogExercise;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.plan.goal.TrainingGoal;
import pro.shapeit.api.training.plan.goal.TrainingGoalRepository;
import pro.shapeit.api.training.plan.unit.CreateTrainingUnitDto;
import pro.shapeit.api.training.plan.unit.TrainingUnit;
import pro.shapeit.api.training.plan.unit.TrainingUnitRepository;
import pro.shapeit.api.training.plan.unit.UpdateTrainingUnitDto;
import pro.shapeit.api.training.plan.unit.exercise.TrainingExercise;
import pro.shapeit.api.training.plan.unit.exercise.TrainingExerciseRepository;
import pro.shapeit.api.training.plan.unit.exercise.UpdateTrainingExerciseDto;
import pro.shapeit.api.training.plan.unit.exercise.set.TrainingSet;
import pro.shapeit.api.training.plan.unit.exercise.set.TrainingSetRepository;
import pro.shapeit.api.training.plan.unit.exercise.set.UpdateTrainingSetDto;

import java.util.List;

import static pro.shapeit.api.common.util.ServiceUtils.updateIfNotNull;

@Service
@RequiredArgsConstructor
class TrainingPlanService {
  private final TrainingPlanRepository trainingPlanRepository;
  private final TrainingUnitRepository trainingUnitRepository;
  private final TrainingExerciseRepository trainingExerciseRepository;
  private final TrainingGoalRepository trainingGoalRepository;
  private final TrainingSetRepository trainingSetRepository;

  // --- Find methods ---

  TrainingSet findTrainingSetByLocalId(String localId) throws ResourceNotFoundException {
    return trainingSetRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Training set does not exist"));
  }

  TrainingExercise findTrainingExerciseByLocalId(String localId) throws ResourceNotFoundException {
    return trainingExerciseRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Training exercise does not exist"));
  }

  List<TrainingPlan> findAllTrainingPlans() {
    return trainingPlanRepository.findAll();
  }

  TrainingPlan findTrainingPlanByLocalId(
      String localId
  ) throws ResourceNotFoundException {
    return trainingPlanRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Training plan does not exist"));
  }

  List<TrainingUnit> findAllTrainingUnitsByTrainingPlanLocalId(
      String planLocalId
  ) throws ResourceNotFoundException {
    if (!trainingPlanRepository.existsByLocalId(planLocalId)) {
      throw new ResourceNotFoundException("Training plan does not exist");
    }
    return trainingUnitRepository.findAllByTrainingPlan_LocalId(planLocalId);
  }

  TrainingUnit findTrainingUnitByTrainingPlanLocalIdAndLocalId(
      String planLocalId,
      String unitLocalId
  ) throws ResourceNotFoundException {
    return trainingUnitRepository.findByTrainingPlan_LocalIdAndLocalId(planLocalId, unitLocalId)
        .orElseThrow(ResourceNotFoundException.supplier("Training unit does not exist"));
  }

  // --- Save methods ---

  TrainingPlan saveTrainingPlan(CreateTrainingPlanDto dto) {
    var plan = new TrainingPlan();
    var goals = dto.goals().stream()
        .map(TrainingGoal::new)
        .toList();
    var savedGoals = trainingGoalRepository.saveAll(goals);

    plan.setName(dto.name());
    plan.setDescription(dto.description());
    plan.setGoals(savedGoals);

    return trainingPlanRepository.save(plan);
  }

  TrainingUnit saveTrainingUnit(TrainingPlan plan, CreateTrainingUnitDto dto) {
    var unit = new TrainingUnit();
    unit.setDayOfWeek(dto.dayOfWeek());
    unit.setTrainingPlan(plan);

    return trainingUnitRepository.save(unit);
  }

  TrainingSet saveTrainingSet(TrainingExercise exercise) {
    var savedSet = trainingSetRepository.save(new TrainingSet());
    exercise.getSets().add(savedSet);
    trainingExerciseRepository.save(exercise);
    return savedSet;
  }

  TrainingExercise saveTrainingExercise(TrainingUnit unit, CatalogExercise catalogExercise) {
    var exercise = new TrainingExercise();
    exercise.setCatalogExercise(catalogExercise);
    var savedExercise = trainingExerciseRepository.save(exercise);
    unit.getExercises().add(savedExercise);
    trainingUnitRepository.save(unit);
    return savedExercise;
  }

  // --- Update methods ---

  TrainingPlan updateTrainingPlan(TrainingPlan plan, UpdateTrainingPlanDto dto) {
    updateIfNotNull(dto.name(), plan::setName);
    updateIfNotNull(dto.description(), plan::setDescription);

    return trainingPlanRepository.save(plan);
  }

  TrainingSet updateTrainingSet(TrainingSet set, UpdateTrainingSetDto dto) {
    updateIfNotNull(dto.intensity(), set::setIntensity);
    updateIfNotNull(dto.rate(), set::setRate);
    updateIfNotNull(dto.intensityType(), set::setIntensityType);
    updateIfNotNull(dto.reps(), set::setReps);
    updateIfNotNull(dto.rest(), set::setRest);
    updateIfNotNull(dto.weight(), set::setWeight);
    updateIfNotNull(dto.weightType(), set::setWeightType);

    return trainingSetRepository.save(set);
  }

  TrainingExercise updateTrainingExercise(
      TrainingExercise exercise,
      CatalogExercise catalogExercise,
      UpdateTrainingExerciseDto dto
  ) {
    updateIfNotNull(catalogExercise, exercise::setCatalogExercise);
    updateIfNotNull(dto.notes(), exercise::setNotes);

    return trainingExerciseRepository.save(exercise);
  }

  TrainingUnit updateTrainingUnit(TrainingUnit unit, UpdateTrainingUnitDto dto) {
    updateIfNotNull(dto.dayOfWeek(), unit::setDayOfWeek);
    updateIfNotNull(dto.notes(), unit::setNotes);
    updateIfNotNull(dto.name(), unit::setName);

    return trainingUnitRepository.save(unit);
  }

  // --- Delete methods ---

  boolean deleteTrainingPlanByLocalId(String planLocalId) {
    return trainingPlanRepository.deleteByLocalIdWithCount(planLocalId) > 0;
  }

  boolean deleteTrainingUnitByTrainingPlanLocalIdAndLocalId(String planLocalId, String unitLocalId) {
    return trainingUnitRepository.deleteByTrainingPlan_LocalIdAndLocalIdWithCount(planLocalId, unitLocalId) > 0;
  }

  boolean deleteTrainingExerciseByLocalId(String localId) {
    return trainingExerciseRepository.deleteByLocalIdWithCount(localId) > 0;
  }

  boolean deleteTrainingSetByLocalId(String localId) {
    return trainingSetRepository.deleteByLocalIdWithCount(localId) > 0;
  }
}
