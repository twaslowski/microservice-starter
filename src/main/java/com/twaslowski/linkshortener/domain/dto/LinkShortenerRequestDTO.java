package com.twaslowski.linkshortener.domain.dto;

import com.twaslowski.linkshortener.domain.value.ShorteningStrategy;
import lombok.Builder;

@Builder
public record LinkShortenerRequestDTO(
    String originalUrl,
    ShorteningStrategy shorteningStrategy,
    String preferredToken
) {

}
