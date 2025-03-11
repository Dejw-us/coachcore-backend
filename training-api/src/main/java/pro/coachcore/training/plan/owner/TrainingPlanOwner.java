package pro.coachcore.training.plan.owner;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CollectionId;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "training_plan_owner")
public class TrainingPlanOwner {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @ElementCollection
  @Column(name = "permission")
  @CollectionTable(name = "training_plan_owner_permissions", joinColumns = @JoinColumn(name = "training_plan_owner_id"))
  private List<String> permissions = new ArrayList<>();

  public void addPermission(Permission permission) {
    permissions.add(permission.name());
  }

  public boolean hasPermission(Permission permission) {
    return permissions.contains(permission.name());
  }

  public static enum Permission {
    VIEW,
    ADMIN,
    SHARE,
    EDIT,
  }
}
