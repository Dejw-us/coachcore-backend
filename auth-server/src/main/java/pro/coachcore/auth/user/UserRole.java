package pro.coachcore.auth.user;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.coachcore.jpa.entity.IdentifiableEntity;

import org.springframework.security.core.GrantedAuthority;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class UserRole extends IdentifiableEntity implements GrantedAuthority {
  private String authority;

  public static boolean isTrainer(UserRole role) {
    return "TRAINER".equals(role.getAuthority());
  }
}
