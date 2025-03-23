package com.twaslowski.linkshortener.service;

import com.twaslowski.linkshortener.domain.entity.ShortLink;
import com.twaslowski.linkshortener.domain.exception.TokenExistsException;
import com.twaslowski.linkshortener.domain.value.ShorteningStrategy;
import com.twaslowski.linkshortener.repository.ShortLinkRepository;
import com.twaslowski.linkshortener.service.shortener.TokenGenerator;
import java.util.Map;
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
  private final URLValidatorService urlValidatorService;
  private final Map<ShorteningStrategy, TokenGenerator> tokenGenerators;

  public ShortLink createShortLink(String url,
                                   ShorteningStrategy shorteningStrategy,
                                   String preferredToken) {
    urlValidatorService.validate(url);

    if (shorteningStrategy == ShorteningStrategy.USER_PREFERENCE) {
      return createPreferredShortlink(url, preferredToken);
    }

    var applicableGenerator = tokenGenerators.get(shorteningStrategy);
    var token = applicableGenerator.createToken(url);
    var shortenedUrl = createShortUrl(token);

    log.info("Shortened {} to {}", url, shortenedUrl);

    return shortLinkRepository.save(ShortLink.builder()
        .originalUrl(url)
        .shortenedUrl(shortenedUrl)
        .token(token)
        .build());
  }

  private ShortLink createPreferredShortlink(String originalUrl, String preferredToken) {
    var existingShortlink = shortLinkRepository.findShortLinkByToken(preferredToken);
    if (existingShortlink.isPresent()) {
      throw new TokenExistsException(preferredToken);
    }

    return shortLinkRepository.save(ShortLink.builder()
        .originalUrl(originalUrl)
        .shortenedUrl(createShortUrl(preferredToken))
        .token(preferredToken)
        .build());
  }

  private String createShortUrl(String token) {
    return "%s/%s".formatted(baseUrl, token);
  }
}
