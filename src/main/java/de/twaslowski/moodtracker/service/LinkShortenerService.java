package de.twaslowski.moodtracker.service;

import de.twaslowski.moodtracker.domain.entity.ShortLink;
import de.twaslowski.moodtracker.repository.ShortLinkRepository;
import de.twaslowski.moodtracker.service.shortener.UrlShortener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkShortenerService {

  @Value("${link-shortener.base-url}")
  private String baseUrl;

  private final ShortLinkRepository shortLinkRepository;
  private final UrlShortener urlTokenGenerator;
  private final URLValidatorService urlValidatorService;

  public ShortLink createShortLink(String url) {
    urlValidatorService.validate(url);
    var token = urlTokenGenerator.createToken(url);
    var shortenedUrl = createShortUrl(token);
    log.info("Shortened {} to {}", url, shortenedUrl);

    return shortLinkRepository.save(ShortLink.builder()
        .originalUrl(url)
        .shortenedUrl(shortenedUrl)
        .token(token)
        .build());
  }

  private String createShortUrl(String token) {
    return "%s/%s".formatted(baseUrl, token);
  }
}
