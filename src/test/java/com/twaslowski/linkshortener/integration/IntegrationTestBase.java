package com.twaslowski.linkshortener.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twaslowski.linkshortener.Annotation.IntegrationTest;
import com.twaslowski.linkshortener.repository.ShortLinkRepository;
import com.twaslowski.linkshortener.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@IntegrationTest
@SpringBootTest
public class IntegrationTestBase {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected ShortLinkRepository shortLinkRepository;

  @Autowired
  protected UserRepository userRepository;

  @BeforeEach
  public void setUp() {
    userRepository.deleteAll();
    shortLinkRepository.deleteAll();
  }
}
