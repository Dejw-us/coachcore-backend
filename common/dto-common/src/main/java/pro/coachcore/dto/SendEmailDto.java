package pro.coachcore.dto;

import java.util.List;

public record SendEmailDto(
    String from,
    List<String> to,
    String subject,
    String content) {
}
