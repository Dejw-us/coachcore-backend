package pro.coachcore.training.plan.exercise;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.coachcore.jpa.entity.BaseEntity;
import pro.coachcore.training.catalog.exercise.CatalogExercise;
import pro.coachcore.training.plan.set.TrainingSet;

import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
public class TrainingExercise extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  private CatalogExercise catalogExercise;

  private String notes;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "training_set_id"),
      joinColumns = @JoinColumn(name = "training_exercise_id")
  )
  private List<TrainingSet> sets = new ArrayList<>();

  

  public CatalogExercise getCatalogExercise() {
    return catalogExercise;
  }

  public String getNotes() {
    return notes;
  }

  public List<TrainingSet> getSets() {
    return sets;
  }

  public void setCatalogExercise(CatalogExercise catalogExercise) {
    this.catalogExercise = catalogExercise;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setSets(List<TrainingSet> sets) {
    this.sets = sets;
  }
}
