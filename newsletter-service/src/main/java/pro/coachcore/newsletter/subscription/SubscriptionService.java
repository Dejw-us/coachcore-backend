package pro.coachcore.newsletter.subscription;

import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.exception.ResourceNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;
  private final SubscriptionMapper subscriptionMapper;

  public List<Subscription> getSubscriptions() {
    return subscriptionRepository.findAll();
  }

  public List<Subscription> getSubscriptions(@Nullable String lang) {
    if (lang == null) {
      return getSubscriptions();
    }
    return subscriptionRepository.findAllByLanguage(lang);
  }

  @Transactional
  public void unsubscribe(String code) {
    if (!subscriptionRepository.existsByCode(code)) {
      throw new ResourceNotFoundException("Subscription with provided code does not exist");
    }
    subscriptionRepository.deleteByCode(code);
  }

  public Subscription subscribe(CreateSubscriptionDto dto) {
    if (isSubscribed(dto.email())) {
      throw new ResourceAlreadyExistsException("Already subscribed");
    }
    return subscriptionRepository.save(subscriptionMapper.map(dto));
  }

  private boolean isSubscribed(String email) {
    return subscriptionRepository.existsByEmail(email);
  }
}
