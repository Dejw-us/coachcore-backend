package pro.coachcore.training.catalog.category;

import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseCategoryService {
  private final ExerciseCategoryRepository exerciseCategoryRepository;

  public List<ExerciseCategory> findAllExerciseCategories() {
    return exerciseCategoryRepository.findAll();
  }

  public ExerciseCategory findExerciseCategory(String localId) throws ResourceNotFoundException {
    return exerciseCategoryRepository.findByLocalId(localId)
        .orElseThrow(() -> new ResourceNotFoundException("Category does not exist"));
  }

  public ExerciseCategory saveExerciseCategory(String name, String description) {
    if (exerciseCategoryRepository.existsByName(name)) {
      return null;
    }
    var category = new ExerciseCategory();

    category.setName(name);
    category.setDescription(description);

    return exerciseCategoryRepository.save(category);
  }
}
