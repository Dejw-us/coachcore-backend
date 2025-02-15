package pro.shapeit.newsletter.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
  boolean existsByEmail(String email);
  void deleteByEmail(String email);
}
