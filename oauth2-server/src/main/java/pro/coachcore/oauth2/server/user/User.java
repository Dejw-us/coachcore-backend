package pro.coachcore.oauth2.server.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pro.coachcore.jpa.id.IdentifiableEntity;
import pro.coachcore.jpa.id.LocalIdEntityListener;
import pro.coachcore.jpa.id.LocalIdInitializer;
import pro.coachcore.oauth2.server.user.role.UserRole;
import pro.coachcore.util.LocalIdUtils;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity(name = "app_user")
@ToString(callSuper = true)
@EntityListeners({ AuditingEntityListener.class, LocalIdEntityListener.class })
public class User implements UserDetails, IdentifiableEntity<String> {
  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String localId;

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
  @JoinTable(inverseJoinColumns = @JoinColumn(name = "user_role_id"), joinColumns = @JoinColumn(name = "user_id"))
  private List<UserRole> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }
}
