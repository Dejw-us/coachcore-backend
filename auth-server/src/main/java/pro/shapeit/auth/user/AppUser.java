package pro.shapeit.auth.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pro.shapeit.jpa.entity.IdentifiableEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class AppUser extends IdentifiableEntity implements UserDetails {
  private String username;

  private String password;

  private String email;

  private String firstName;

  private String lastName;

  private LocalDate dateOfBirth;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @ManyToMany(fetch = FetchType.EAGER)
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
