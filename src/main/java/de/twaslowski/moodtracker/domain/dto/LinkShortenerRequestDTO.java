package de.twaslowski.moodtracker.domain.dto;

import lombok.Builder;

@Builder
public record LinkShortenerRequestDTO(
    String originalUrl
) {

}
