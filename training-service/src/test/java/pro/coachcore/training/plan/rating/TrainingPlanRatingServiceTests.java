package pro.coachcore.training.plan.rating;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.AuditorAware;

import pro.coachcore.training.plan.TrainingPlanService;

@ExtendWith(MockitoExtension.class)
public class TrainingPlanRatingServiceTests {
  @Mock
  private TrainingPlanRatingRepository trainingPlanRatingRepository;

  @Mock
  private TrainingPlanService trainingPlanService;

  @Mock
  private AuditorAware<String> auditorAware;

  @InjectMocks
  private TrainingPlanRatingService trainingPlanRatingService;

  @Test
  void createRating_shouldThrowException_whenAboveRange() {
    assertThrows(IllegalArgumentException.class, () -> trainingPlanRatingService.createRating("planId", 11));
    assertThrows(IllegalArgumentException.class, () -> trainingPlanRatingService.createRating("planId", -1));
  }
}
