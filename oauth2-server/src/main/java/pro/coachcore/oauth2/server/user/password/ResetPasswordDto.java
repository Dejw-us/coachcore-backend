package pro.coachcore.oauth2.server.user.password;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class ResetPasswordDto {
  @NotBlank(message = "Enter password")
  private String password;

  @NotBlank(message = "Repeat password")
  private String repeatedPassword;

  @NotBlank(message = "Invalid token")
  private String token;

  public boolean passwordsMatch() {
    return password.equals(repeatedPassword);
  }
}
