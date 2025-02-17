package pro.coachcore.newsletter.subscription;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Subscription {
  @Id
  @GeneratedValue
  private Long id;

  private String email;

  private String language;

  private Long newslettersRead;
}
