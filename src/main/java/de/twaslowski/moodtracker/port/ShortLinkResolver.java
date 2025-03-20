package de.twaslowski.moodtracker.port;

import de.twaslowski.moodtracker.service.LinkResolutionService;
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

  private final LinkResolutionService linkResolutionService;

  @GetMapping(value = "/{stub}")
  public ResponseEntity<?> getOriginalLink(@PathVariable String stub) {
    log.info("Processing request for stub {}", stub);
    return ResponseEntity.status(302).body(linkResolutionService.resolveStub(stub));
  }
}
