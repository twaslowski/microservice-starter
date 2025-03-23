package com.twaslowski.linkshortener.domain.exception;

public class InvalidURLException extends IllegalArgumentException {

  private static final String message = "%s is not a valid url";

  public InvalidURLException(String url) {
    super(message.formatted(url));
  }
}
