package pro.shapeit.auth.user;

import java.time.LocalDate;

public record PublicUserDto(
    String username,
    String publicId,
    String createdAt,
    boolean isTrainer
) {
}
