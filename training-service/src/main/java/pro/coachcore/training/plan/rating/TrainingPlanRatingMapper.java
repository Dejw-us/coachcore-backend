package pro.coachcore.training.plan.rating;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainingPlanRatingMapper {
  TrainingPlanRatingDto map(TrainingPlanRating rating);
}
