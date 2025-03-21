package de.twaslowski.moodtracker.integration;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.twaslowski.moodtracker.domain.entity.ShortLink;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
public class ShortLinkResolverControllerTest extends IntegrationTestBase {

  @Test
  @SneakyThrows
  void shouldPerformRedirect() {
    var shortLink = shortLinkRepository.save(ShortLink.builder()
        .token("some-id")
        .originalUrl("https://my-awesome-website.com")
        .shortenedUrl("https://shortlink.com/some-id")
        .build());

    mockMvc.perform(MockMvcRequestBuilders.get(format("/%s", shortLink.getToken()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(303));
  }
}
