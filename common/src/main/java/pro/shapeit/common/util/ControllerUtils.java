package pro.shapeit.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;
import pro.shapeit.common.dto.MessageDto;

@UtilityClass
public class ControllerUtils {
  public static ResponseEntity<MessageDto> deleteResponse(
      boolean isDeleted,
      String objectName
  ) {
    if (isDeleted) {
      var message = StringUtils.capitalizeFirstLetter(objectName).concat(" has been deleted.");
      return ResponseEntity
          .ok(new MessageDto(message));
    }
    var message = "Failed to delete ".concat(objectName).concat(".");
    return ResponseEntity
        .badRequest()
        .body(new MessageDto(message));
  }
}
