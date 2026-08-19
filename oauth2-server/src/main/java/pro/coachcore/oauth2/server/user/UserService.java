package pro.coachcore.oauth2.server.user;

import static pro.coachcore.util.ServiceUtils.updateIf;
import static pro.coachcore.util.ServiceUtils.updateIfNotNull;

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
import pro.coachcore.oauth2.server.user.role.UserRoleService;

/**
 * Service class for managing users and roles.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final UserRoleService userRoleService;
  private final PasswordEncoder passwordEncoder;
  private final MessageService messageService;

  /**
   * Retrieves a user by username.
   *
   * @param username the username of the user
   * @return the User entity
   * @throws ResourceNotFoundException if the user does not exist
   */
  public User getUserByUsername(String username) throws ResourceNotFoundException {
    return userRepository.findByUsername(username).orElseThrow(
        ResourceNotFoundException.supplier(messageService.getMessage("user.not-found")));
  }

  public User getUser(String identifier, Boolean byUsername) {
    if (byUsername) {
      return getUserByUsername(identifier);
    }
    return getUserByLocalId(identifier);
  }

  /**
   * Retrieves a user by their local ID.
   *
   * @param localId the local ID of the user
   * @return the User entity
   * @throws ResourceNotFoundException if the user does not exist
   */
  public User getUserByLocalId(String localId) throws ResourceNotFoundException {
    return userRepository.findByLocalId(localId).orElseThrow(
        ResourceNotFoundException.supplier(messageService.getMessage("user.not-found")));
  }

  /**
   * Registers an admin user if one does not already exist.
   *
   * @param username the username for the admin
   * @param password the password for the admin
   * @return the created User entity or null if an admin already exists
   */
  public User registerAdmin(String username, String password) {
    if (userRepository.existsByUsername(username)) {
      log.info("Admin user already exists. Skipping creating default admin...");
      return null;
    }
    var user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.getRoles().add(userRoleService.findRole("ADMIN"));
    return userRepository.save(user);
  }

  /**
   * Registers a new user with the default "USER" role.
   *
   * @param dto the DTO containing user registration details
   * @return the created User entity
   */
  public User registerUser(RegisterUserDto dto) {
    var user = new User();
    user.setUsername(dto.getUsername());
    user.setEmail(dto.getEmail());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.getRoles().add(userRoleService.findRole("USER"));
    return userRepository.save(user);
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
      if (username.contains("@")) {
        return userRepository.findByEmail(username).orElseThrow();
      } else {
        return userRepository.findByUsername(username).orElseThrow();
      }
    } catch (Exception exception) {
      throw new UsernameNotFoundException(
          messageService.getMessage("username.not-found", username));
    }
  }

  public User updateUser(User user, UpdateUserDto dto) {
    updateIfNotNull(dto.description(), user::setDescription);
    var updated = updateIf(dto.username(), user::setUsername, this::canUpdateUsername);
    return userRepository.save(user);
  }

  public boolean isUsernameTaken(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean isEmailTaken(String email) {
    return userRepository.existsByEmail(email);
  }

  private boolean canUpdateUsername(String username) {
    if (isUsernameTaken(username)) {
      throw new ResourceAlreadyExistsException("Username is taken");
    }
    return true;
  }
}
