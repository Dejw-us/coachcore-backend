package pro.coachcore.newsletter.subscription;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
  @Id
  @GeneratedValue
  private Long id;

  private String email;

  private String language;
 
  private Long newslettersRead;
  
  private String code;
  
  @PrePersist
  private void setCodeIfNull() {
    if (code == null) {
      code = UUID.randomUUID().toString();
    }
  }
}
