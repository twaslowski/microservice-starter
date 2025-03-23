package com.twaslowski.linkshortener.service;

import com.twaslowski.linkshortener.domain.entity.ShortLink;
import com.twaslowski.linkshortener.domain.exception.TokenNotFoundException;
import com.twaslowski.linkshortener.repository.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenResolutionService {

  private final ShortLinkRepository shortLinkRepository;

  @SneakyThrows
  public String resolveToken(String token) {
    return shortLinkRepository.findShortLinkByToken(token)
        .map(ShortLink::getOriginalUrl)
        .orElseThrow(TokenNotFoundException::new);
  }
}
