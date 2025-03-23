package com.twaslowski.linkshortener.domain.dto;

import lombok.Builder;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy;

@Builder
public record LinkShortenerRequestDTO(
    String originalUrl,
    Strategy shorteningStrategy,
    String preferredToken
) {

}
