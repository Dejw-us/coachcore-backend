package pro.coachcore.training.plan.parameter;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterDisplayRepository extends JpaRepository<ParameterDisplay, Long> {
  Optional<ParameterDisplay> findByTrainingUnit_LocalId(String localId);
}
