package pro.coachcore.training.plan.parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import pro.coachcore.training.plan.unit.TrainingUnit;

@Data
@Entity
@Table(name = "parameter_display")
public class ParameterDisplay {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_unit_id")
  private TrainingUnit trainingUnit;

  @Column(name = "display_intensity")
  private Boolean displayIntensity = false;

  @Column(name = "display_rate")
  private Boolean displayRate = false;

  @Column(name = "display_weight")
  private Boolean displayWeight = false;

  public void updateDisplay(DisplayUpdater updater, Boolean display) {
    switch (updater) {
      case DisplayUpdater.INTENSITY -> setDisplayIntensity(display);
      case DisplayUpdater.RATE -> setDisplayRate(display);
      case DisplayUpdater.WEIGHT -> setDisplayWeight(display);
    }
  }

  public enum DisplayUpdater {
    INTENSITY, RATE, WEIGHT
  }
}
