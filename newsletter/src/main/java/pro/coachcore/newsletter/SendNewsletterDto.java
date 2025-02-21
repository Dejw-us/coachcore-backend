package pro.coachcore.newsletter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record SendNewsletterDto(
  @NotEmpty
  String subject,
  @NotEmpty
  String content,
  @NotEmpty
  @Pattern(regexp = "^(pl|en)$")
  String language
) {
}
