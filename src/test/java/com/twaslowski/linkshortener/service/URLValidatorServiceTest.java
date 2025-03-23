package com.twaslowski.linkshortener.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.twaslowski.linkshortener.domain.exception.InvalidURLException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class URLValidatorServiceTest {

  private final URLValidatorService validatorService = new URLValidatorService();

  @ParameterizedTest
  @ValueSource(strings = {
      "https://www.example.com",
      "http://example.com/path/to/page",
      "https://example.com:8080/path?query=value",
      "https://192.168.1.1/admin",
  })
  void shouldPassValidUrls(String url) {
    validatorService.validate(url);
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "example.com",                      // no protocol
      "https://invalid domain.com",       // space in domain
      "ftp://myserver.com",               // only http(s) supported
      "http:/example.com"                 // single slash
  })
  void shouldThrowForInvalidUrls(String url) {
    assertThatThrownBy(() -> validatorService.validate(url))
        .isInstanceOf(InvalidURLException.class);
  }
}