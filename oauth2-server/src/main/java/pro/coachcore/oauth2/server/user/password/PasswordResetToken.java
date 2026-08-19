package pro.coachcore.oauth2.server.user.password;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import pro.coachcore.oauth2.server.user.User;

@Data
@Entity
@Table(name = "password_reset_token")
@EntityListeners(AuditingEntityListener.class)
public class PasswordResetToken {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "token")
  private String token;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @JoinColumn(name = "user_id")
  @OneToOne(fetch = FetchType.EAGER)
  private User user;
}
