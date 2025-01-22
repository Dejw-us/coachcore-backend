package pro.shapeit.backend.catalog.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseCategoryRepository extends JpaRepository<ExerciseCategory, Long> {
  Optional<ExerciseCategory> findByLocalId(String localId);
  boolean existsByLocalId(String localId);
  boolean existsByName(String name);
}
