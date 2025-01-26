package pro.shapeit.auth.user;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import pro.shapeit.jpa.entity.IdentifiableEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class UserRole extends IdentifiableEntity implements GrantedAuthority {
  private String authority;
}
