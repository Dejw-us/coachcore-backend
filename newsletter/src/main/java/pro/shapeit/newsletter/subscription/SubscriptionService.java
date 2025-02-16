package pro.shapeit.newsletter.subscription;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.shapeit.exception.ResourceAlreadyExistsException;
import pro.shapeit.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;
  private final UnsubscribeCodeRepository unsubscribeCodeRepository;
  private final SubscriptionMapper subscriptionMapper;

  public List<Subscription> getSubscriptions() {
    return subscriptionRepository.findAll();
  }

  public List<Subscription> getSubscriptions(String lang) {
    return getSubscriptions().stream()
        .filter(subscription -> subscription.getLanguage().equals(lang))
        .toList();
  }

  public UnsubscribeCode getOrGenerateUnsubscribeCode(Subscription subscription) {
    return unsubscribeCodeRepository.findBySubscription_Id(subscription.getId())
        .orElseGet(() -> generateUnsubscribeCode(subscription));
  }

  public UnsubscribeCode generateUnsubscribeCode(Subscription subscription) {
    var code = new UnsubscribeCode();
    code.setSubscription(subscription);
    code.setCode(UUID.randomUUID().toString());
    return unsubscribeCodeRepository.save(code);
  }

  public void unsubscribe(String code) {
    var unsubscribeCode = getUnsubscribeCode(code);
    var subscription = unsubscribeCode.getSubscription();
    log.info("Removed subscription code: {}", code);
    unsubscribeCodeRepository.delete(unsubscribeCode);
  }

  public void subscribe(CreateSubscriptionDto dto) {
    if (isSubscribed(dto.email())) {
      throw new ResourceAlreadyExistsException("Already subscribed");
    }
    var subscription = subscriptionMapper.map(dto);
    subscriptionRepository.save(subscription);
  }

  private boolean isSubscribed(String email) {
    return subscriptionRepository.existsByEmail(email);
  }

  private UnsubscribeCode getUnsubscribeCode(String code) {
    return unsubscribeCodeRepository.findByCode(code)
        .orElseThrow(ResourceNotFoundException.supplier("Code not found"));
  }
}
