package pro.coachcore.newsletter;

public record NewsletterSendingStatus(
    String message,
    String emailStatus,
    int emailsSent) {
}
