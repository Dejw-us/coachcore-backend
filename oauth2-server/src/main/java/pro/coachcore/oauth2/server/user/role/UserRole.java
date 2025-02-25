package pro.coachcore.oauth2.server.user.role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
public class UserRole implements GrantedAuthority {
  @Id
  @GeneratedValue
  private Long id;

  private String authority;

  public static boolean isTrainer(UserRole role) {
    return "TRAINER".equals(role.getAuthority());
  }
}
