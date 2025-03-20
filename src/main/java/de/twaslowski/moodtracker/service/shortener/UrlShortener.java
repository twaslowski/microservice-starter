package de.twaslowski.moodtracker.service.shortener;

public interface UrlShortener {

  String createToken(String originalUrl);
}
