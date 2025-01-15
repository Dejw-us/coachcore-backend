package pro.shapeit.api.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import pro.shapeit.api.common.util.LocalIdUtils;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String localId;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @CreatedBy
  @Column(updatable = false)
  private String createdBy;

  @LastModifiedBy
  private String updatedBy;

  @PrePersist
  private void setupLocalId() {
    if (localId == null) {
      localId = LocalIdUtils.random();
    }
  }
}
