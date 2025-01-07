package pro.shapeit.api.training.exercise.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.common.id.LocalId;
import pro.shapeit.api.training.exercise.category.ExerciseCategory;
import pro.shapeit.api.training.exercise.category.ExerciseCategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogExerciseService {
  private final CatalogExerciseRepository catalogExerciseRepository;
  private final ExerciseCategoryRepository exerciseCategoryRepository;
  private final CatalogExerciseMapper catalogExerciseMapper;

  public List<CatalogExercise> finalAllCatalogExercises() {
    return catalogExerciseRepository.findAll();
  }

  public CatalogExercise findCatalogExercise(String localId) throws ResourceNotFoundException {
    return catalogExerciseRepository.findByLocalId(localId)
        .orElseThrow(() -> new ResourceNotFoundException("Catalog catalogExercise does not exist"));
  }

  public CatalogExercise saveCatalogExercise(CreateCatalogExerciseDto dto, ExerciseCategory category) throws ResourceNotFoundException {
    var exercise = new CatalogExercise();

    exercise.setName(dto.name());
    exercise.setLocalId(LocalId.random());
    exercise.setCategory(category);

    return catalogExerciseRepository.save(exercise);
  }
}
