package com.twaslowski.linkshortener.service.shortener;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class RandomUrlShortener implements UrlShortener {

  @Override
  public String createToken(String originalUrl) {
    return UUID.randomUUID().toString();
  }
}
