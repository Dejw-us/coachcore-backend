package pro.coachcore.auth.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
  Optional<UserRole> findByAuthority(String authority);

  boolean existsByAuthority(String authority);
}
