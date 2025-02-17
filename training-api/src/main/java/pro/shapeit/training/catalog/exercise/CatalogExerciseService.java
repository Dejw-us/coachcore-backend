package pro.shapeit.training.catalog.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import pro.coachcore.exception.ResourceNotFoundException;
import pro.shapeit.training.catalog.category.ExerciseCategory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogExerciseService {
  private final CatalogExerciseRepository catalogExerciseRepository;

  public List<CatalogExercise> finalAllCatalogExercises() {
    return catalogExerciseRepository.findAll();
  }

  public CatalogExercise findCatalogExerciseByLocalId(String localId) throws ResourceNotFoundException {
    return catalogExerciseRepository.findByLocalId(localId)
        .orElseThrow(() -> new ResourceNotFoundException("Catalog catalogExercise does not exist"));
  }

  public CatalogExercise saveCatalogExercise(CreateCatalogExerciseDto dto, ExerciseCategory category) throws ResourceNotFoundException {
    if (catalogExerciseRepository.existsByName(dto.name())) {
      return null;
    }
    var exercise = new CatalogExercise();

    exercise.setName(dto.name());
    exercise.setCategory(category);

    return catalogExerciseRepository.save(exercise);
  }
}
