package de.twaslowski.moodtracker.service;

import org.springframework.stereotype.Service;

@Service
public class UrlStubProvider {

  public String provideStub(String originalUrl) {
    return originalUrl;
  }
}
