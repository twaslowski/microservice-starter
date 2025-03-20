package de.twaslowski.moodtracker.port;

import de.twaslowski.moodtracker.domain.exception.InvalidURLException;
import de.twaslowski.moodtracker.domain.exception.TokenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingAdvice {

  @ExceptionHandler(TokenNotFoundException.class)
  public final ResponseEntity<ErrorResponse> handleDeckNotFound(TokenNotFoundException ex) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidURLException.class)
  public final ResponseEntity<ErrorResponse> handleInvalidUrl(InvalidURLException ex) {
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}
