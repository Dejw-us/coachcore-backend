package pro.coachcore.oauth2.server.user.role;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
  Optional<UserRole> findByAuthority(String authority);

  boolean existsByAuthority(String authority);
}
