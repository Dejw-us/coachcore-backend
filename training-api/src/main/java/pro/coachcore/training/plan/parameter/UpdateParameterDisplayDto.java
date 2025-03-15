package pro.coachcore.training.plan.parameter;

public record UpdateParameterDisplayDto(
    ParameterDisplay.DisplayUpdater updater,
    Boolean display) {
}