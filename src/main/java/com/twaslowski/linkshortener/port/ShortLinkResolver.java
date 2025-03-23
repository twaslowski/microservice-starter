package com.twaslowski.linkshortener.port;

import static org.springframework.http.HttpStatus.SEE_OTHER;

import com.twaslowski.linkshortener.service.TokenResolutionService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShortLinkResolver {

  private final TokenResolutionService tokenResolutionService;

  @SneakyThrows
  @GetMapping(value = "/{token}")
  public ResponseEntity<?> getOriginalLink(@PathVariable String token) {
    log.info("Processing request for token {}", token);
    var resolvedOriginalLink = tokenResolutionService.resolveToken(token);
    var headers = setRedirectHeader(resolvedOriginalLink);
    return new ResponseEntity<>(headers, SEE_OTHER);
  }

  @SneakyThrows
  private HttpHeaders setRedirectHeader(String resolvedOriginalLink) {
    var headers = new HttpHeaders();
    headers.setLocation(new URI(resolvedOriginalLink));
    return headers;
  }
}
