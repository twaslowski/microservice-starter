package de.twaslowski.moodtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class LinkShortener {

  public static void main(String[] args) {
    SpringApplication.run(LinkShortener.class, args);
  }
}
