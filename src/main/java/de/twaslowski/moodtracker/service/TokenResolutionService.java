package de.twaslowski.moodtracker.service;

import de.twaslowski.moodtracker.domain.entity.ShortLink;
import de.twaslowski.moodtracker.domain.exception.TokenNotFoundException;
import de.twaslowski.moodtracker.repository.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenResolutionService {

  private final ShortLinkRepository shortLinkRepository;

  public String resolveToken(String token) {
    return shortLinkRepository.findShortLinkByToken(token)
        .map(ShortLink::getOriginalUrl)
        .orElseThrow(TokenNotFoundException::new);
  }
}
