package com.twaslowski.linkshortener.domain.exception;

public class UserExistsException extends RuntimeException {

  public UserExistsException(String userId) {
    super("User with email hash %s already exists".formatted(userId));
  }
}
