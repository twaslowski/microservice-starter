package com.twaslowski.linkshortener.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.twaslowski.linkshortener.domain.dto.LinkShortenerRequestDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
public class ShortLinkControllerTest extends IntegrationTestBase {

  @Test
  @SneakyThrows
  void shouldCreateAndReturnShortLink() {
    var request = LinkShortenerRequestDTO.builder()
        .originalUrl("https://some-url.com")
        .build();

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shortlink")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.originalUrl").value(request.originalUrl()));
    // .andExpect(jsonPath("$.shortUrl").value()); // implement matcher
  }
}
