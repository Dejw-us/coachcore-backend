package pro.shapeit.auth.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  Optional<AppUser> findByUsername(String username);
}
