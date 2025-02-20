package pro.coachcore.newsletter.subscription;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "newslettersRead", ignore = true)
  Subscription map(CreateSubscriptionDto dto);

  SubscriptionDto map(Subscription subscription);

  List<SubscriptionDto> map(List<Subscription> models);
}
