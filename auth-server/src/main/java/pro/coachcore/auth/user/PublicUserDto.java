package pro.shapeit.auth.user;

public record PublicUserDto(
    String username,
    String id,
    String createdAt,
    boolean isTrainer
) {
}
