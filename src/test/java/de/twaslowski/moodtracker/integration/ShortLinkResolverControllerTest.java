package de.twaslowski.moodtracker.integration;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.twaslowski.moodtracker.domain.entity.ShortLink;
import de.twaslowski.moodtracker.repository.ShortLinkRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
public class ShortLinkResolverControllerTest extends IntegrationTestBase {

  @Autowired
  private ShortLinkRepository shortLinkRepository;

  @Test
  @SneakyThrows
  void shouldPerformRedirect() {
    var shortLink = shortLinkRepository.save(ShortLink.builder()
        .originalUrl("https://my-awesome-website.com")
        .shortenedUrl("https://shortlink.com/some-id")
        .build());

    mockMvc.perform(MockMvcRequestBuilders.get(format("/%s", shortLink.getId()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(302));
  }
}
