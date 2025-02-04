package pro.shapeit.image.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pro.shapeit.dto.ErrorsDto;
import pro.shapeit.exception.DefaultGlobalExceptionHandler;

import java.io.FileNotFoundException;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler extends DefaultGlobalExceptionHandler {
  @ExceptionHandler(FileNotFoundException.class)
  ResponseEntity<ErrorsDto> handleFileNotFound(
      FileNotFoundException exception,
      HttpServletRequest request
  ) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorsDto.create("FILE_NOT_FOUND", "Cannot find requested file", request));
  }
}
