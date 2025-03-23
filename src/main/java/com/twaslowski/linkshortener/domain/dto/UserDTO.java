package com.twaslowski.linkshortener.domain.dto;

import lombok.Builder;

@Builder
public record UserDTO(
    String email,
    String password
) {

}
