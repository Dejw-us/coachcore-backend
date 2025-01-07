package pro.shapeit.api.training.exercise.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.common.id.LocalId;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseCategoryService {
  private final ExerciseCategoryRepository exerciseCategoryRepository;
  private final ExerciseCategoryMapper exerciseCategoryMapper;

  public List<ExerciseCategoryDto> findAllExerciseCategories() {
    return exerciseCategoryRepository.findAll().stream()
        .map(exerciseCategoryMapper::map)
        .toList();
  }

  public ExerciseCategory findExerciseCategory(String localId) throws ResourceNotFoundException {
    return exerciseCategoryRepository.findByLocalId(localId)
        .orElseThrow(() -> new ResourceNotFoundException("Category does not exist"));
  }

  public ExerciseCategoryDto saveExerciseCategory(String name) {
    if (exerciseCategoryRepository.existsByName(name)) {
      return null;
    }
    var category = new ExerciseCategory();

    category.setLocalId(LocalId.random());
    category.setName(name);

    return exerciseCategoryMapper.map(exerciseCategoryRepository.save(category));
  }
}
