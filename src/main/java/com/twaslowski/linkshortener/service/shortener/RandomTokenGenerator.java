package com.twaslowski.linkshortener.service.shortener;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class RandomTokenGenerator implements TokenGenerator {

  @Override
  public String createToken(String preferredToken) {
    return UUID.randomUUID().toString();
  }
}
