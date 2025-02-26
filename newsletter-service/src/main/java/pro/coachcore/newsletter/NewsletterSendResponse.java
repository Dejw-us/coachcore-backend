package pro.coachcore.newsletter;

public record NewsletterSendResponse(
    String status,
    int emailsSent) {
}
