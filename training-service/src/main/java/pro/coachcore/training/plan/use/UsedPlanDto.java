package pro.coachcore.training.plan.use;

import pro.coachcore.training.plan.TrainingPlanDto;

public record UsedPlanDto(String userId, TrainingPlanDto usedPlan) {

}
