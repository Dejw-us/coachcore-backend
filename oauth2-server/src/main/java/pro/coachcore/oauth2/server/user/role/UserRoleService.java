package pro.coachcore.oauth2.server.user.role;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;

@Service
@RequiredArgsConstructor
public class UserRoleService {
  private final UserRoleRepository userRoleRepository;
  private final MessageService messageService;

  /**
   * Saves a new default role in the system.
   *
   * @param authority the name of the role (e.g., "USER", "ADMIN")
   * @return the saved UserRole entity
   * @throws ResourceAlreadyExistsException if the role already exists
   */
  public UserRole saveDefaultRole(String authority) {
    if (userRoleRepository.existsByAuthority(authority)) {
      throw new ResourceAlreadyExistsException(authority);
    }
    var role = new UserRole();
    role.setAuthority(authority);
    return userRoleRepository.save(role);
  }

  /**
   * Finds a role by authority name.
   *
   * @param authority the role name
   * @return the UserRole entity
   * @throws ResourceNotFoundException if the role does not exist
   */
  public UserRole findRole(String authority) throws ResourceNotFoundException {
    return userRoleRepository.findByAuthority(authority).orElseThrow(
        ResourceNotFoundException.supplier(messageService.getMessage("role.not-found")));
  }
}
