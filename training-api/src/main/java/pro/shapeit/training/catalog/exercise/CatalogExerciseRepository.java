package pro.shapeit.training.catalog.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogExerciseRepository extends JpaRepository<CatalogExercise, Long> {
  Optional<CatalogExercise> findByLocalId(String localId);
  boolean existsByName(String name);
}
