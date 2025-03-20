package de.twaslowski.moodtracker.service;

import de.twaslowski.moodtracker.domain.exception.InvalidURLException;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class URLValidatorService {

  // Taken from: https://regexr.com/39nr7
  private static final Pattern REGEX = Pattern.compile("^https?://(?:www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b[-a-zA-Z0-9()@:%_+.~#?&/=]*$");

  public void validate(String url) throws InvalidURLException {
    if (!REGEX.matcher(url).find()) {
      throw new InvalidURLException(url);
    }
  }
}
