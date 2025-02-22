package pro.coachcore.training.plan.unit;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pro.coachcore.training.plan.exercise.TrainingExerciseMapper;
import pro.coachcore.training.plan.set.TrainingSetMapper;

import java.util.List;

import static java.lang.Boolean.TRUE;

@Mapper(uses = {TrainingSetMapper.class, TrainingExerciseMapper.class}, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingUnitMapper {
  @Mapping(source = "localId", target = "id")
  TrainingUnitDto map(TrainingUnit model);

  @Mapping(source = "localId", target = "id")
  TrainingUnitPreviewDto mapToPreview(TrainingUnit model);

  List<TrainingUnitDto> map(List<TrainingUnit> models);

  List<TrainingUnitPreviewDto> mapToPreview(List<TrainingUnit> models);

  default Object map(TrainingUnit model, Boolean isPreview) {
    if (TRUE.equals(isPreview)) {
      return mapToPreview(model);
    }
    return map(model);
  }

  default Object map(List<TrainingUnit> models, Boolean isPreview) {
    if (TRUE.equals(isPreview)) {
      return mapToPreview(models);
    }
    return map(models);
  }
}
