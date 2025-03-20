package de.twaslowski.moodtracker.exception;

public class NotAValidURLException extends IllegalArgumentException {

  private static final String message = "%s is not a valid url";

  public NotAValidURLException(String url) {
    super(message.formatted(url));
  }
}
