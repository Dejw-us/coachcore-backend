package pro.coachcore.training.plan.owner;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CollectionId;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pro.coachcore.training.plan.TrainingPlan;

@Entity
@Table(name = "training_plan_owner")
public class TrainingPlanOwner {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_plan_id")
  private TrainingPlan trainingPlan;

  @ElementCollection
  @Column(name = "permission")
  @CollectionTable(name = "training_plan_owner_permissions", joinColumns = @JoinColumn(name = "training_plan_owner_id"))
  private List<String> permissions = new ArrayList<>();

  public void addPermission(Permission permission) {
    permissions.add(permission.name());
  }

  public boolean canEdit() {
    return hasPermission(Permission.EDIT);
  }

  public boolean canShare() {
    return hasPermission(Permission.SHARE);
  }

  public boolean canView() {
    return hasPermission(Permission.VIEW);
  }

  public boolean hasPermission(Permission permission) {
    if (permission == Permission.ADMIN) {
      return true;
    }
    return permissions.contains(permission.name());
  }

  public static enum Permission {
    VIEW,
    ADMIN,
    SHARE,
    EDIT,
  }
}
