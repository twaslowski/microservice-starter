package com.twaslowski.linkshortener.domain.exception;

public class TokenExistsException extends RuntimeException {

  public TokenExistsException(String token) {
    super("Shortlink with token %s already exists".formatted(token));
  }
}
