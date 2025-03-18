package pro.coachcore.training.catalog.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.training.catalog.category.ExerciseCategory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogExerciseService {
  private final CatalogExerciseRepository catalogExerciseRepository;

  public List<CatalogExercise> findAllCatalogExercises() {
    return catalogExerciseRepository.findAll();
  }

  public CatalogExercise findCatalogExerciseByLocalId(String localId)
      throws ResourceNotFoundException {
    return catalogExerciseRepository.findByLocalId(localId)
        .orElseThrow(ResourceNotFoundException.supplier("Catalog catalogExercise does not exist"));
  }

  public CatalogExercise saveCatalogExercise(CreateCatalogExerciseDto dto,
      ExerciseCategory category) {
    if (catalogExerciseRepository.existsByName(dto.name())) {
      throw new ResourceAlreadyExistsException("Catalog catalogExercise already exists");
    }
    var exercise = new CatalogExercise();

    exercise.setName(dto.name());
    exercise.setCategory(category);

    return catalogExerciseRepository.save(exercise);
  }
}
