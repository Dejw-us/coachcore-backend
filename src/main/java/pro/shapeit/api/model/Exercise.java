package pro.shapeit.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import pro.shapeit.api.annotation.validation.ValidLocalId;

@Entity
public class Exercise {
  @Id
  @GeneratedValue
  private Long id;

  @ValidLocalId
  private String localId;

  private String name;
}
