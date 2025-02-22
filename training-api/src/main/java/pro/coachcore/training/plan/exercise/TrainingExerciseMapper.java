package pro.coachcore.training.plan.exercise;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pro.coachcore.training.catalog.exercise.CatalogExerciseMapper;
import pro.coachcore.training.plan.set.TrainingSetMapper;

@Mapper(uses = {TrainingSetMapper.class, CatalogExerciseMapper.class}, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingExerciseMapper {
  @Mapping(target = "id", source = "localId")
  @Mapping(target = "notes", source = "notes")
  TrainingExerciseDto map(TrainingExercise exercise);
}
