package de.twaslowski.moodtracker.service;

import de.twaslowski.moodtracker.domain.entity.ShortLink;
import de.twaslowski.moodtracker.domain.exception.StubNotFoundException;
import de.twaslowski.moodtracker.repository.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkResolutionService {

  private final ShortLinkRepository shortLinkRepository;

  public String resolveStub(String stub) {
    return shortLinkRepository.findById(stub)
        .map(ShortLink::getOriginalUrl)
        .orElseThrow(StubNotFoundException::new);
  }
}
