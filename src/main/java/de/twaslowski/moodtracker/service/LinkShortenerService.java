package de.twaslowski.moodtracker.service;

import de.twaslowski.moodtracker.domain.entity.ShortLink;
import de.twaslowski.moodtracker.repository.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkShortenerService {

  @Value("${link-shortener.base-url}")
  private String baseUrl;

  private final ShortLinkRepository shortLinkRepository;
  private final UrlStubProvider urlStubProvider;
  private final URLValidatorService urlValidatorService;

  public ShortLink createShortLink(String url) {
    urlValidatorService.validate(url);
    var urlStub = urlStubProvider.provideStub(url);
    return shortLinkRepository.save(ShortLink.builder()
        .shortUrl(urlStub)
        .originalUrl(url)
        .build());
  }
}
