package pro.coachcore.oauth2.server.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.ToString;
import pro.coachcore.jpa.id.IdentifiableEntity;
import pro.coachcore.jpa.id.LocalIdEntityListener;
import pro.coachcore.oauth2.server.user.role.UserRole;

@Data
@Entity(name = "app_user")
@ToString(callSuper = true)
@EntityListeners({AuditingEntityListener.class, LocalIdEntityListener.class})
public class User implements UserDetails, IdentifiableEntity<String> {
  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String localId;

  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", unique = true, nullable = true)
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToMany(fetch = FetchType.EAGER)
  @Column(name = "roles")
  @JoinTable(inverseJoinColumns = @JoinColumn(name = "user_role_id"),
      joinColumns = @JoinColumn(name = "user_id"))
  private List<UserRole> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }
}
