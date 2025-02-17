package pro.coachcore.newsletter.subscription;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record CreateSubscriptionDto(
    @Email
    @NotEmpty
    String email,
    @NotEmpty
    @Pattern(regexp = "^(pl|en)$")
    String language
) {

}
