package pro.coachcore.training.plan.parameter;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParameterDisplayMapper {
  ParameterDisplayDto map(ParameterDisplay display);
}
