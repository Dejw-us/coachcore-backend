package pro.shapeit.api.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
  RESOURCE_NOT_FOUND,
  NOT_AUTHORIZED,
  RESOURCE_ALREADY_EXISTS,
  RESOURCE_FAILED_TO_UPDATE,
  DTO_VALIDATION_ERROR
}
