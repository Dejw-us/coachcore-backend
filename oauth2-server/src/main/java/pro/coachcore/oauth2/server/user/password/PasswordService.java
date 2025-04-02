package pro.coachcore.oauth2.server.user.password;

import static java.lang.String.format;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pro.coachcore.dto.SendEmailDto;
import pro.coachcore.exception.GlobalHandlerRuntimeException;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.oauth2.server.email.EmailService;
import pro.coachcore.oauth2.server.user.User;
import pro.coachcore.oauth2.server.user.UserRepository;

@Service
@RequiredArgsConstructor
public class PasswordService {
  private static final Long TOKEN_LIFE_MINUTES = 10L;

  private final PasswordResetTokenRepository passwordResetTokenRepository;
  private final UserRepository userRepository;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;

  public PasswordResetToken generateToken(String email) {
    var token = new PasswordResetToken();
    var user = userRepository.findByEmail(email).orElseThrow();

    token.setToken(UUID.randomUUID().toString());
    token.setUser(user);

    return passwordResetTokenRepository.save(token);
  }

  public void sendResetLink(PasswordResetToken token, String toEmail) {
    var content = format("<a href=\"http://localhost:8089/password/reset?token=%s\">Reset password</a>",
        token.getToken());
    var email = new SendEmailDto("no-reply@coachcore.pro", List.of(toEmail), "Password reset", content);
    emailService.sendEmail(email);
  }

  public User resetPassword(String newPassword, PasswordResetToken token) {
    if (isTokenExpired(token)) {
      throw GlobalHandlerRuntimeException.create(
          "Token is expired",
          HttpStatus.UNAUTHORIZED,
          "TOKEN_EXPIRED");
    }

    var user = token.getUser();
    user.setPassword(passwordEncoder.encode(newPassword));
    token.setUser(null);
    passwordResetTokenRepository.delete(token);

    return userRepository.save(user);
  }

  public PasswordResetToken getToken(String token) {
    return passwordResetTokenRepository.findByToken(token)
        .orElseThrow(ResourceNotFoundException.supplier("Reset token not found"));
  }

  public boolean isTokenExpired(PasswordResetToken token) {
    return token.getCreatedAt().plusMinutes(TOKEN_LIFE_MINUTES).isBefore(LocalDateTime.now());
  }
}
