package de.twaslowski.moodtracker.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.twaslowski.moodtracker.Annotation.IntegrationTest;
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
}
