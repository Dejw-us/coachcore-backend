package pro.shapeit.newsletter.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnsubscribeCodeRepository extends JpaRepository<UnsubscribeCode, Long> {
  Optional<UnsubscribeCode> findByCode(String code);
  Optional<UnsubscribeCode> findBySubscription_Id(Long id);
}
