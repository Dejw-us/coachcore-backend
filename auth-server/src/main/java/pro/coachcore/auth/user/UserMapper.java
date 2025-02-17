package pro.coachcore.auth.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "isTrainer", expression = "java(isTrainer(entity.getRoles()))")
  @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd")
  @Mapping(target = "id", source = "localId")
  PublicUserDto mapToPublic(AppUser entity);

  default boolean isTrainer(List<UserRole> roles) {
    return roles.stream().anyMatch(UserRole::isTrainer);
  }
}
