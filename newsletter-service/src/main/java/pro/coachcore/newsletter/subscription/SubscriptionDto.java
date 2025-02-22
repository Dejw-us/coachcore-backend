package pro.coachcore.newsletter.subscription;

public record SubscriptionDto(
    String email,
    String language,
    int newslettersRead
) {
}
