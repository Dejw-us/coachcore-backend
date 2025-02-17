package pro.coachcore.newsletter.subscription;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
  @Mapping(target = "id", ignore = true)
  Subscription map(CreateSubscriptionDto dto);

  SubscriptionDto map(Subscription subscription);
}
