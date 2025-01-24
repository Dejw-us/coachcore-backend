package pro.shapeit.auth.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pro.shapeit.jpa.entity.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class AppUser extends BaseEntity implements UserDetails {
  private String username;

  private String password;

  private String email;

  private String firstName;

  private String lastName;

  private LocalDate dateOfBirth;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "user_role_id"),
      joinColumns = @JoinColumn(name = "user_id")
  )
  private List<UserRole> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }
}
