package pro.shapeit.training.catalog.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.backend.common.exception.resource.ResourceNotFoundException;
import pro.shapeit.backend.catalog.category.ExerciseCategory;
import pro.shapeit.backend.catalog.category.ExerciseCategoryRepository;

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
    exercise.setCategory(category);

    return catalogExerciseRepository.save(exercise);
  }
}
