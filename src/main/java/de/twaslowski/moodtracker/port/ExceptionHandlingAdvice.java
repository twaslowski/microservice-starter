package de.twaslowski.moodtracker.port;

import de.twaslowski.moodtracker.domain.exception.StubNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingAdvice {

  @ExceptionHandler(StubNotFoundException.class)
  public final ResponseEntity<ErrorResponse> handleDeckNotFound(StubNotFoundException ex) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
