package com.twaslowski.linkshortener.service.shortener;

public interface TokenGenerator {

  String createToken(String originalUrl);
}
