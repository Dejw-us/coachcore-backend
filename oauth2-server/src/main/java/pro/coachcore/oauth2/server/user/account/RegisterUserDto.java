package pro.coachcore.oauth2.server.user.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordsMatch
public final class RegisterUserDto {
  @UniqueUsername
  @NotBlank(message = "Username is required")
  @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
  private String username;

  @UniqueEmail
  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email")
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
  private String password;

  @NotBlank(message = "Confirm password")
  private String confirmPassword;
}
