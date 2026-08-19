package pro.coachcore.oauth2.server.user.password;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
  Optional<PasswordResetToken> findByUser_Email(String email);

  Optional<PasswordResetToken> findByToken(String token);

  boolean existsByUser_Email(String email);

  void deleteByUser_Email(String email);
}
