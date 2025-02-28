package pro.coachcore.training.catalog.exercise;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.Data;
import pro.coachcore.jpa.id.IdentifiableEntity;
import pro.coachcore.jpa.id.LocalIdEntityListener;
import pro.coachcore.training.catalog.category.ExerciseCategory;

@Data
@Entity(name = "catalog_exercise")
@EntityListeners({ LocalIdEntityListener.class, AuditingEntityListener.class })
public class CatalogExercise implements IdentifiableEntity<String> {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false, name = "local_id")
  private String localId;

  @Column(nullable = false, name = "name")
  private String name;

  @Column(name = "category_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private ExerciseCategory category;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDate createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDate updatedAt;

  @CreatedBy
  @Column(name = "created_by")
  private String createdBy;

  @LastModifiedBy
  @Column(name = "updated_by")
  private String updatedBy;
}
