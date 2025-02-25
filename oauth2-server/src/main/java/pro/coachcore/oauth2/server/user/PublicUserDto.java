package pro.coachcore.oauth2.server.user;

public record PublicUserDto(
    String username,
    String id,
    String createdAt,
    boolean isTrainer
) {
}
