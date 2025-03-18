package pro.coachcore.training.plan.set;

public record TrainingSetDto(
    String id,
    Integer reps,
    Double intensity,
    String rate,
    Double restSeconds,
    Double weight,
    Long index) {
}