package pro.coachcore.oauth2.server.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.oauth2.server.user.account.IllegalUserCredentialsException;
import pro.coachcore.oauth2.server.user.account.RegisterUserDto;
import pro.coachcore.oauth2.server.user.role.UserRole;
import pro.coachcore.oauth2.server.user.role.UserRoleRepository;
import pro.coachcore.exception.ResourceNotFoundException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository appUserRepository;
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;

  private static final Supplier<ResourceNotFoundException> USER_NOT_FOUND = ResourceNotFoundException.supplier("User does not exist");

  public UserRole saveDefaultRole(String authority) {
    if (userRoleRepository.existsByAuthority(authority)) {
      return null;
    }
    var role = new UserRole();
    role.setAuthority(authority);
    return userRoleRepository.save(role);
  }

  public User findUserByUsername(String username) throws ResourceNotFoundException {
    return appUserRepository.findByUsername(username)
        .orElseThrow(USER_NOT_FOUND);
  }

  public User findUserByLocalId(String localId) throws ResourceNotFoundException {
    return appUserRepository.findByLocalId(localId)
        .orElseThrow(USER_NOT_FOUND);
  }

  public UserRole findRole(String authority) throws ResourceNotFoundException {
    return userRoleRepository.findByAuthority(authority)
        .orElseThrow(ResourceNotFoundException.supplier("User role does not exist"));
  }

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

  public User registerUser(
      RegisterUserDto dto
  ) throws ResourceNotFoundException {
    var user = new User();
    user.setUsername(dto.getUsername());
    user.setEmail(dto.getEmail());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.getRoles().add(findRole("USER"));

    return appUserRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      return findUserByUsername(username);
    } catch (ResourceNotFoundException exception) {
      throw new UsernameNotFoundException("User does no exist");
    }
  }

  public boolean isUsernameTaken(String username) {
    return appUserRepository.existsByUsername(username);
  }

  public boolean isEmailTaken(String email) {
    return appUserRepository.existsByEmail(email);
  }
}
