package pro.shapeit.auth.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.shapeit.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final AppUserRepository appUserRepository;
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;

  public UserRole saveRole(String authority) {
    if (userRoleRepository.existsByAuthority(authority)) {
      return null;
    }
    var role = new UserRole();
    role.setAuthority(authority);
    return userRoleRepository.save(role);
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
    return appUserRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User does no exist"));
  }
}
