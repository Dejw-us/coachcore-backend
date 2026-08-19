package pro.coachcore.training.plan.save;

import pro.coachcore.training.plan.TrainingPlanDto;

public record SavedPlanDto(String userId, TrainingPlanDto savedPlan) {

}
