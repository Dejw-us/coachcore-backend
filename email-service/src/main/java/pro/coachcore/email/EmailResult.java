package pro.coachcore.email;

public record EmailResult(
  EmailStatus status,
  int emailsSent
) {
}
