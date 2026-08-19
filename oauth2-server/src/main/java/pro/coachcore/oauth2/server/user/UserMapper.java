package pro.coachcore.oauth2.server.user;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.coachcore.oauth2.server.user.role.UserRole;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "isTrainer", expression = "java(isTrainer(entity.getRoles()))")
  @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd")
  @Mapping(target = "id", source = "localId")
  PublicUserDto mapToPublic(User entity);

  default boolean isTrainer(List<UserRole> roles) {
    return roles.stream().anyMatch(UserRole::isTrainer);
  }
}
