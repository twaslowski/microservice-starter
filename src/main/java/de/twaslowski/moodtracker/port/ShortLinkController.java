package de.twaslowski.moodtracker.port;

import de.twaslowski.moodtracker.domain.dto.LinkShortenerRequestDTO;
import de.twaslowski.moodtracker.domain.entity.ShortLink;
import de.twaslowski.moodtracker.service.LinkShortenerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ShortLinkController {

  private final LinkShortenerService linkShortenerService;

  @PostMapping("/shorten-link")
  public ResponseEntity<ShortLink> shortenLink(@Valid @RequestBody LinkShortenerRequestDTO request) {
    return ResponseEntity.ok(linkShortenerService.createShortLink(request.originalUrl()));
  }
}
