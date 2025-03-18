package pro.coachcore.oauth2.server.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.lang.message.MessageService;
import pro.coachcore.oauth2.server.user.account.RegisterUserDto;
import pro.coachcore.oauth2.server.user.role.UserRole;
import pro.coachcore.oauth2.server.user.role.UserRoleRepository;

/**
 * Service class for managing users and roles.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository appUserRepository;
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;
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
   * Retrieves a user by username.
   *
   * @param username the username of the user
   * @return the User entity
   * @throws ResourceNotFoundException if the user does not exist
   */
  public User getUser(String username) throws ResourceNotFoundException {
    return appUserRepository.findByUsername(username).orElseThrow(
        ResourceNotFoundException.supplier(messageService.getMessage("user.not-found")));
  }

  /**
   * Retrieves a user by their local ID.
   *
   * @param localId the local ID of the user
   * @return the User entity
   * @throws ResourceNotFoundException if the user does not exist
   */
  public User getUserByLocalId(String localId) throws ResourceNotFoundException {
    return appUserRepository.findByLocalId(localId).orElseThrow(
        ResourceNotFoundException.supplier(messageService.getMessage("user.not-found")));
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

  /**
   * Registers an admin user if one does not already exist.
   *
   * @param username the username for the admin
   * @param password the password for the admin
   * @return the created User entity or null if an admin already exists
   */
  public User registerAdmin(String username, String password) {
    if (appUserRepository.existsByUsername(username)) {
      log.info("Admin user already exists. Skipping creating default admin...");
      return null;
    }
    var user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.getRoles().add(findRole("ADMIN"));
    return appUserRepository.save(user);
  }

  /**
   * Registers a new user with the default "USER" role.
   *
   * @param dto the DTO containing user registration details
   * @return the created User entity
   * @throws ResourceNotFoundException if the "USER" role is not found
   */
  public User registerUser(RegisterUserDto dto) throws ResourceNotFoundException {
    var user = new User();
    user.setUsername(dto.getUsername());
    user.setEmail(dto.getEmail());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.getRoles().add(findRole("USER"));
    return appUserRepository.save(user);
  }

  /**
   * Loads a user by username (required by Spring Security).
   *
   * @param username the username of the user
   * @return the User entity
   * @throws UsernameNotFoundException if the user is not found
   */
  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      return getUser(username);
    } catch (ResourceNotFoundException exception) {
      throw new UsernameNotFoundException(
          messageService.getMessage("username.not-found", username));
    }
  }

  /**
   * Checks if a username is already taken.
   *
   * @param username the username to check
   * @return true if the username exists, false otherwise
   */
  public boolean isUsernameTaken(String username) {
    return appUserRepository.existsByUsername(username);
  }

  /**
   * Checks if an email is already taken.
   *
   * @param email the email to check
   * @return true if the email exists, false otherwise
   */
  public boolean isEmailTaken(String email) {
    return appUserRepository.existsByEmail(email);
  }
}
