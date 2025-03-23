package com.twaslowski.linkshortener.service.shortener;

public interface UrlShortener {

  String createToken(String originalUrl);
}
