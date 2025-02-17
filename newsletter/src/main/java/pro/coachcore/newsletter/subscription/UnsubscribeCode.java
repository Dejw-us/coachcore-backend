package pro.coachcore.newsletter.subscription;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UnsubscribeCode {
  @Id
  @GeneratedValue
  private Long id;

  private String code;

  @OneToOne(cascade = CascadeType.ALL)
  @MapsId
  @JoinColumn(name = "subscription_id", unique = true, nullable = false)
  private Subscription subscription;
}
