package de.twaslowski.moodtracker.port;

import de.twaslowski.moodtracker.service.TokenResolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShortLinkResolver {

  private final TokenResolutionService tokenResolutionService;

  @GetMapping(value = "/{token}")
  public ResponseEntity<?> getOriginalLink(@PathVariable String token) {
    log.info("Processing request for token {}", token);
    return ResponseEntity.status(302).body(tokenResolutionService.resolveToken(token));
  }
}
