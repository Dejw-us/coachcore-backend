package pro.coachcore.dto;

import java.util.List;

public record ValidationErrorDto(
    String field,
    List<String> errorMessages
) {
}
