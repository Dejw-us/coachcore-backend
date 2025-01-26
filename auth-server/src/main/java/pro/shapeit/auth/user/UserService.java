package pro.shapeit.auth.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pro.shapeit.exception.ResourceNotFoundException;

import java.util.function.Supplier;

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

  public AppUser findUserByUsername(String username) throws ResourceNotFoundException {
    return appUserRepository.findByUsername(username)
        .orElseThrow(USER_NOT_FOUND);
  }

  public AppUser findUserByLocalId(String localId) throws ResourceNotFoundException {
    return appUserRepository.findByLocalId(localId)
        .orElseThrow(USER_NOT_FOUND);
  }

  public UserRole findRole(String authority) throws ResourceNotFoundException {
    return userRoleRepository.findByAuthority(authority)
        .orElseThrow(ResourceNotFoundException.supplier("User role does not exist"));
  }

  public AppUser registerUser(
      RegisterUserDto dto
  ) throws ResourceNotFoundException {
    if (!dto.getPassword().equals(dto.getConfirmPassword())) {
      throw new IllegalArgumentException("Passwords do not match");
    }
    if (appUserRepository.existsByUsername(dto.getUsername())) {
      throw new IllegalArgumentException("Username is already taken");
    }
    if (appUserRepository.existsByEmail(dto.getEmail())) {
      throw new IllegalArgumentException("Email is already in use");
    }

    var user = new AppUser();
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
}
