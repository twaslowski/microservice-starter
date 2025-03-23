package com.twaslowski.linkshortener.config;

import com.twaslowski.linkshortener.domain.value.ShorteningStrategy;
import com.twaslowski.linkshortener.service.shortener.RandomTokenGenerator;
import com.twaslowski.linkshortener.service.shortener.TokenGenerator;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TokenGeneratorConfiguration {

  private final RandomTokenGenerator randomTokenGenerator;

  @Bean
  public Map<ShorteningStrategy, TokenGenerator> tokenGenerators() {
    return Map.of(
        ShorteningStrategy.RANDOM_UUID, randomTokenGenerator
    );
  }
}
